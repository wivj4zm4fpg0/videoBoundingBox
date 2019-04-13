package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
        //メインペイン終了---------------------------

        primaryStage.setTitle("BoundingBoxVideo");
        primaryStage.setScene(new Scene(borderPane, 1200, 560));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
