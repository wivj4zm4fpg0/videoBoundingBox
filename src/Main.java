import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        SelectBox selectBox = new SelectBox();

        Pane root = new Pane();
        Scene scene = new Scene(root, 320, 240);

        root.getChildren().add(new Rectangle(320, 240, Color.WHITE));
        Label label = new Label("Please drag and drop the video");
        label.setFont(new Font("Arial", 20));
        label.setLayoutX(20);
        label.setLayoutY(100);
        root.getChildren().add(label);

        root.setOnDragOver(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        root.setOnDragDropped(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                board.getFiles().forEach(file -> {
                    // reset
                    root.getChildren().removeAll();
                    selectBox.resetRect();
                    // create media player
                    Media media = new Media("file:///" + file.getAbsolutePath().replace('\\', '/'));
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnReady(primaryStage::sizeToScene);
                    mediaPlayer.setAutoPlay(true);
                    MediaControl mediaControl = new MediaControl(mediaPlayer, media, primaryStage, selectBox);

                    mediaControl.getChildren().add(selectBox.getRectangle());
                    for (int i = 0; i < selectBox.getEllipse().length; i++) {
                        mediaControl.getChildren().add(selectBox.getEllipse()[i]);
                        mediaControl.getChildren().add(selectBox.getCenterEllipse()[i]);
                    }
                    root.getChildren().add(mediaControl);
                });
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
