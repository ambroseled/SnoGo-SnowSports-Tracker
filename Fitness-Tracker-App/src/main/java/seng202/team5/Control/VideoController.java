package seng202.team5.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import seng202.team5.Model.Activity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Observable;



//TODO Do we want to build a map in the corner at the same time as the video plays??





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
        mediaPlayer.play();
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
