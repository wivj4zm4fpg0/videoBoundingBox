package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane mainMedia = new Pane();

        mainMedia.getChildren().add(new Rectangle(640, 480, Color.WHITE));

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

        ObservableList<String> listRecord = FXCollections.observableArrayList();

        //スリーペイン----------------------
        BorderPane threePane = new BorderPane();
        threePane.setCenter(mainMedia);
        threePane.setLeft(new LeftPain());
        threePane.setRight(new RightPain(listRecord));
        //スリーペイン終了------------------

        //メインペイン----------------------------------
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new MainMenuBar(primaryStage, listRecord));
        borderPane.setCenter(threePane);
        //メインペイン終了---------------------------

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(new Scene(borderPane, 1150, 560));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
