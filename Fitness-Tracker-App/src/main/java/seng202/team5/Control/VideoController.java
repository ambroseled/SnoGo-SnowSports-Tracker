package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;

public class VideoController {
    @FXML
    private MediaView mediaView;

    private Media media;
    private MediaPlayer mediaPlayer;

    public void selectVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File f = fileChooser.showOpenDialog(null);
        try {
            playVideo(f.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void playVideo(String path) {

        //yo
    }
}
