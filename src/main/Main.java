package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    static String dirPath = null;
    static LeftPain leftPain = new LeftPain();
    static String annotationPath = null;

    @Override
    public void start(Stage primaryStage) {

        ObservableList<String> listRecord = FXCollections.observableArrayList();

        //スリーペイン----------------------
        BorderPane threePane = new BorderPane();
        CenterPain centerPain = new CenterPain();
        threePane.setCenter(centerPain);
        threePane.setLeft(leftPain);
        threePane.setRight(new RightPain(centerPain, listRecord));
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
