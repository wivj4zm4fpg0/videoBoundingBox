import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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

        mainMedia.getChildren().add(new Rectangle(340, 240, Color.WHITE));
        Label label = new Label("Please drag and drop the video");
        label.setFont(new Font("Arial", 20));
        label.setLayoutX(20);
        label.setLayoutY(100);
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

        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);
        vBox.getChildren().addAll(menuBar, mainMedia);
        Scene scene = new Scene(vBox, 320, 240);

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
