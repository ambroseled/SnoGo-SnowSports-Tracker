package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import org.apache.commons.collections.functors.FalsePredicate;

import java.io.File;

public class VideoController {
    @FXML
    private MediaView mediaView;

    @FXML
    private Button toggleButton;

    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean playing = false;

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

    public void playVideo(String path) {

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
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
}
