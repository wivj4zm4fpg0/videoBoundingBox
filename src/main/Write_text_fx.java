package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;

public class Write_text_fx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Button button = new Button("click");
        root.setCenter(button);
        button.setOnAction(event -> {

            try {
                File file = new File("test.txt");
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                printWriter.println("Hello World!!");
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();

    }
}
