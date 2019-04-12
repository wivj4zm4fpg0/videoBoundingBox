package main;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class CenterPain extends Pane {
    CenterPain() {

        this.getChildren().add(new Rectangle(640, 480, Color.WHITE));

        this.setOnDragOver(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        this.setOnDragDropped(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                board.getFiles().forEach(file -> {
                    // reset
                    this.getChildren().clear();

                    readVideo(file.getAbsolutePath());
                });
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });
    }

    void readVideo(String path) {
        this.getChildren().clear();
        Media media = new Media("file:///" + path.replace('\\', '/'));
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        this.getChildren().add(new MediaControl(mediaPlayer, media));
    }
}
