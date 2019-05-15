package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    static String dirPath = "";
    static String annotationPath = "";
    static String openedVideo = "";
    static Label annotationLabel = new Label("ファイルが選択されていません");
    static Label videoLabel = new Label("ファイルが選択されていません");
    static LeftPain leftPain = new LeftPain();
    static int currentHours = 0;
    static int currentMinute = 0;
    static int currentSecond = 0;
    static CenterPain centerPain = new CenterPain();
    static MediaPlayer mp = null;
    static SelectBox selectBox = new SelectBox();
    static boolean atEndOfMedia = false;

    @Override
    public void start(Stage primaryStage) {

        ObservableList<String> listRecord = FXCollections.observableArrayList();

        //スリーペイン----------------------
        BorderPane threePane = new BorderPane();
        threePane.setCenter(centerPain);
        threePane.setLeft(leftPain);
        threePane.setRight(new RightPain(listRecord));
        //スリーペイン終了------------------

        //メインペイン----------------------------------
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new MainMenuBar(primaryStage, listRecord));
        borderPane.setCenter(threePane);
        borderPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                MediaPlayer.Status status = mp.getStatus();

                if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                    // don't do anything in these states
                    return;
                }

                if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            }
        });
        borderPane.setOnKeyPressed(event -> {
            switch (event.getCode().toString()) {
                case "U":
                    mp.setRate(mp.getRate() + 0.5);
                    System.out.println("speed = " + mp.getRate());
                    break;
                case "D":
                    mp.setRate(mp.getRate() - 0.5);
                    System.out.println("speed = " + mp.getRate());
                    break;
                case "Z":
                    selectBox.undo();
                    selectBox.print();
                    break;
                case "Y":
                    selectBox.redo();
                    selectBox.print();
                    break;
                case "B":
                case "M":
                case "N":
                    MediaPlayer.Status status = mp.getStatus();

                    if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                        // don't do anything in these states
                        return;
                    }

                    if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED) {
                        // rewind the movie if we're sitting at the end
                        if (atEndOfMedia) {
                            mp.seek(mp.getStartTime());
                            atEndOfMedia = false;
                        }
                        mp.play();
                    } else {
                        mp.pause();
                    }
            }
        });
        //メインペイン終了---------------------------

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(new Scene(borderPane, 1200, 560));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
