package seng202.team5.Control;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import seng202.team5.DataManipulation.DataBaseController;
import seng202.team5.Model.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class VideoController {

    private boolean videoPlaying = false;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private ChoiceBox activityChoice;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button toggleButton;

    @FXML
    private TableView videosTable;

    @FXML
    private TableColumn<File, String> videosColumn;

    @FXML
    private Button removeButton;

    @FXML
    private Text hRate;

    @FXML
    private Text speed;

    private DataBaseController db = HomeController.getDb();

    private ArrayList<Activity> activities;

    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean playing = false;
    private ObservableList<File> videoList = FXCollections.observableArrayList();


    /**
     * Function called when the tab is first selected to initialize the controller
     */
    public void initialize() {
        String path = System.getProperty("user.home");
        new File(path + "/SnoGo/Videos").mkdirs();
        mediaView.setFitHeight(360);
        mediaView.setFitWidth(640);
        mediaView.setPreserveRatio(true);
        fillTable();
        checkVideoSelected();

        webEngine = webView.getEngine();
        webEngine.load(VideoController.class.getResource("/View/map.html").toExternalForm());

    }

    /**
     * Function that creates a file chooser, and copies the contents of the selected file into the local storage folder
     */
    public void addVideoToApp() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Video File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MP4", "*.mp4")
            );
            File f = fileChooser.showOpenDialog(null);
            File dest = new File(System.getProperty("user.home") + "/SnoGo/Videos/" + f.getName());
            try {
                Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            fillTable();
        } catch (Exception e) {
            DialogController.displayError("Unable to add video");
        }

    }

    /**
     * Gets the selected file from the videos table and removes it from the local storage folder
     */
    public void removeSelectedVideo() {

        try {
            File selectedFile = (File) videosTable.getSelectionModel().getSelectedItem();
            selectedFile.delete();
            fillTable();
        } catch (Exception e) {
            DialogController.displayError("Unable to remove video");
        }
    }

    @FXML
    /**
     * Enabling remove button if a video is selected
     */
    private void checkVideoSelected(){
        if (videosTable.getSelectionModel().getSelectedItem() != null){
            removeButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
        }
    }

    /**
     * Moves the marker on the minimap to the end of the current route of the user
     * @param route current Route the user has taken up until the current point in the video
     */
    public void map(Route route) {
        String scriptToExecute = "moveMarker(" + route.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    /**
     * Function that searches for the start time of the video within an activity to see if it can be successfully synced to the video
     */
    public void bindVideo() {

        videoPlaying = false;
        Activity selectedActivity = (Activity) activityChoice.getValue();
        try {

            File video = (File) videosTable.getSelectionModel().getSelectedItem();

            try {
                Path file = new File(video.getAbsolutePath()).toPath();
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
                String dateCreated = df.format(attr.creationTime().toMillis());

                System.out.println(dateCreated);


                ArrayList<DataPoint> dataSet = selectedActivity.getDataSet().getDataPoints();

                int startIndex = 0;
                boolean found = false;
                for (DataPoint x : dataSet) {

                    if (x.getFormattedDate().equals(dateCreated)) {

                        startIndex = dataSet.indexOf(x) - 60;
                        hRate.setText(Integer.toString(x.getHeartRate()));
                        speed.setText(Double.toString(x.getSpeed()));
                        System.out.println(startIndex);
                        found = true;
                    }

                }

                if (!found) {
                    DialogController.displayError("Start time of video not found within selected activity");
                    return;
                }

                playVideo(video.getAbsolutePath(), dataSet, startIndex);

            } catch (Exception e) {
                DialogController.displayError("Unable to retrieve data from video and activity");
            }

        } catch (Exception e) {
            DialogController.displayError("No video selected");
        }
    }


    /**
     * Given a path to a video, a set of data, and a starting index, this function begins a thread to loop through the data
     * from the starting index based on the time of the video, and updates the heart rate and speed values, and also updates the map
     * using nested threads.
     * @param path
     * @param dataSet
     * @param startIndex
     */
    public void playVideo(String path, ArrayList<DataPoint> dataSet, int startIndex) {

        videoPlaying = true;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        Runnable videoThread = new Runnable() {

            public void run(){


                try {


                    int endIndex = dataSet.indexOf(dataSet.get(dataSet.size() - 1));
                    int previous = (int) mediaPlayer.getCurrentTime().toSeconds();


                    List subList = dataSet.subList(startIndex, endIndex);
                    ArrayList<DataPoint> subListArray = new ArrayList<DataPoint>();
                    subListArray.addAll(subList);
                    Route route = new Route(subListArray);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String scriptToExecute = "displayMiniRoute(" + route.toJSONArray() + ");";
                                webEngine.executeScript(scriptToExecute);
                            } catch (Exception e) {
                                DialogController.displayError("Unable to show map, check your internet connection");
                            }

                        }
                    });

                    DataPoint thing = dataSet.get(startIndex);
                    System.out.println(thing.getDateTime());

                    int loops = 0;
                    while (videoPlaying) {
                        if (((int) mediaPlayer.getCurrentTime().toSeconds()) != previous) {
                            System.out.println((int) mediaPlayer.getCurrentTime().toSeconds());
                            previous = (int) mediaPlayer.getCurrentTime().toSeconds();


                            int currentRate = dataSet.get(startIndex + loops).getHeartRate();
                            hRate.setText(Integer.toString(currentRate));
                            double currentSpeed = dataSet.get(startIndex + loops).getSpeed();
                            speed.setText(Double.toString(currentSpeed));

                            subList = dataSet.subList(startIndex, startIndex + loops);
                            subListArray.addAll(subList);
                            Route route2 = new Route(subListArray);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        map(route2);
                                    } catch (Exception e) {
                                    }
                                }
                            });
                            loops += 1;
                        }

                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }
        };

        Thread thread = new Thread(videoThread);
        thread.start();
    }

    /**
     * Toggles the mediaPlayer to playing or paused and also switch the text on the control button
     */
    public void togglePlayback() {
        if (mediaPlayer != null) {
            if (playing) {
                mediaPlayer.pause();
                playing = false;
                toggleButton.setText("Play");
            } else {
                mediaPlayer.play();
                playing = true;
                toggleButton.setText("Pause");
            }
        }

    }

    /**
     * Populates the videos table with all the filenames of videos found in the local storage directory
     */
    public void fillTable() {
        videosTable.getItems().clear();
        File[] fileList = new File(System.getProperty("user.home") + "/SnoGo/Videos").listFiles();
        for (File file: fileList) {
            videoList.add(file);

        }
        videosColumn.setCellValueFactory(new PropertyValueFactory("name"));
        videosTable.setItems(videoList);


    }

    /**
     * Populate the choice box with the activities of the current user
     */
    public void setChoiceBox() {
        ArrayList<Activity> inputActivities = db.getActivities(HomeController.getCurrentUser().getId());
        activities = inputActivities;

        ObservableList<Activity> activityNames = FXCollections.observableArrayList();
        for (Activity activity: activities) {
            activityNames.add(activity);
        }
        activityChoice.setItems(activityNames);

    }
}
