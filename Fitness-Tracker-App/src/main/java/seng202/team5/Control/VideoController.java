package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team5.Model.Activity;
import javafx.util.Duration;
import seng202.team5.Model.DataPoint;
import seng202.team5.Model.DataSet;
import seng202.team5.Model.User;
import sun.swing.AccumulativeRunnable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;



//TODO Do we want to build a map in the corner at the same time as the video plays??  Yep





public class VideoController {
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

    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean playing = false;
    private ObservableList<File> videoList = FXCollections.observableArrayList();

    public void initialize() {
        String path = System.getProperty("user.home");
        new File(path + "/SnoGo/Videos").mkdirs();
        mediaView.setFitHeight(360);
        mediaView.setFitWidth(640);
        mediaView.setPreserveRatio(true);
        fillTable();
        checkVideoSelected();
    }

    public void selectVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File f = fileChooser.showOpenDialog(null);
        try {
            playVideo(f.getAbsolutePath());
            playing = true;
            toggleButton.setText("Pause");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayVideo() {
        try {
            File video = (File) videosTable.getSelectionModel().getSelectedItem();
            playVideo(video.getAbsolutePath());
            playing = false;
            toggleButton.setText("Play");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addVideoToApp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File f = fileChooser.showOpenDialog(null);
        File dest = new File(System.getProperty("user.home") + "/SnoGo/Videos/" + f.getName());
        try {
            Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fillTable();
    }

    public void removeSelectedVideo() {
        File selectedFile = (File) videosTable.getSelectionModel().getSelectedItem();
        selectedFile.delete();
        fillTable();
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

    public void playVideo(String path) {

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        ObservableMap data = media.getMetadata();




        Runnable myRunnable = new Runnable() {

            public void run(){

                try {
                    Path file = new File(path).toPath();
                    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    String dateCreated = df.format(attr.creationTime().toMillis());

                    System.out.println(dateCreated);

                    ArrayList<Activity> runs = HomeController.getCurrentUser().getActivities();
                    Activity run2 = runs.get(7);
                    System.out.println("Runnable running");

                    ArrayList<DataPoint> dataSet = run2.getDataSet().getDataPoints();

                    int startIndex = 0;

                    for (DataPoint x : dataSet) {
                        if (x.getDateTime().toString() == dateCreated) {
                            startIndex = x.getId();
                            hRate.setText(Integer.toString(x.getHeartRate()));
                        }
                    }


                    int previous = (int) mediaPlayer.getCurrentTime().toSeconds();
                    int current = (int) mediaPlayer.getCurrentTime().toSeconds();
                    int loops = 0;
                    while (true) {
                        previous = (int) mediaPlayer.getCurrentTime().toSeconds();

                        if ((int) mediaPlayer.getCurrentTime().toSeconds() != previous) {
                            System.out.println((int) mediaPlayer.getCurrentTime().toSeconds());
                            previous = (int) mediaPlayer.getCurrentTime().toSeconds();


                            int currentRate = dataSet.get(startIndex + loops).getHeartRate();
                            hRate.setText(Integer.toString(currentRate));
                            loops += 1;
                        }

                    }

                } catch (Exception e) {}


            }
        };

        Thread thread = new Thread(myRunnable);
        thread.start();







        //mediaPlayer.play();
    }

    public void rotateVideo() {
        mediaView.setRotate(mediaView.getRotate() + 90);
    }

    public void toggleStretch() {
        if (mediaView.isPreserveRatio()) {
            mediaView.setPreserveRatio(false);
        } else {
            mediaView.setPreserveRatio(true);
        }
    }

    public void togglePlayback() {
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

    public void fillTable() {
        videosTable.getItems().clear();
        File[] fileList = new File(System.getProperty("user.home") + "/SnoGo/Videos").listFiles();
        for (File file: fileList) {
            videoList.add(file);

        }
        videosColumn.setCellValueFactory(new PropertyValueFactory("name"));
        videosTable.setItems(videoList);


    }
}
