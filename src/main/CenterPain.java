package main;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class CenterPain extends BorderPane {

    CenterPain() {

        this.setCenter(new Rectangle(640, 480, Color.WHITE));

        this.setOnDragOver(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        this.setOnDragDropped(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                board.getFiles().forEach(file -> readVideo(file.getAbsolutePath()));
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });
    }

    void readVideo(String path) {
        this.getChildren().clear();
        this.setCenter(new MediaControl(path));
    }
}
