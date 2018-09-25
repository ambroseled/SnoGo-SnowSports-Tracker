package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class videoControl {


    @FXML
    private MediaView video;


    @FXML
    public void play() {
        MediaPlayer player = new MediaPlayer(new Media("pingu.mp3"));
        video.setMediaPlayer(player);
    }
}
