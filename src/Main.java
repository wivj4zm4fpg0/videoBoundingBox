import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane mainMedia = new Pane();

        mainMedia.getChildren().add(new Rectangle(320, 240, Color.WHITE));
        Label label = new Label("Please drag and drop the video");
        label.setFont(new Font("Arial", 20));
        label.setLayoutX(20);
        label.setLayoutY(105);
        label.setAlignment(Pos.CENTER);
        mainMedia.getChildren().add(label);

        mainMedia.setOnDragOver(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        mainMedia.setOnDragDropped(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                board.getFiles().forEach(file -> {
                    // reset
                    mainMedia.getChildren().clear();
                    // create media player
                    Media media = new Media("file:///" + file.getAbsolutePath().replace('\\', '/'));
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnReady(primaryStage::sizeToScene);
                    mediaPlayer.setAutoPlay(true);
                    MediaControl mediaControl = new MediaControl(mediaPlayer, media, primaryStage);

                    mainMedia.getChildren().add(mediaControl);
                });
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(mainMedia);

        Scene scene = new Scene(root, 320, 240);

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
