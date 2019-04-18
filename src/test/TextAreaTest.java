package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextAreaTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        TextArea textArea = new TextArea();
        try {
            Scanner s = new Scanner(new File("test.txt")).useDelimiter("\n");
            while (s.hasNext()) {
                if (s.hasNextInt()) { // check if next token is an int
                    textArea.appendText(s.nextInt() + "\n"); // display the found integer
                } else {
                    textArea.appendText(s.next() + "\n"); // else read the next token
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        textArea.setPrefSize(400, 400);
        pane.getChildren().addAll(textArea);

        primaryStage.setScene(new Scene(pane, 400, 400));
        primaryStage.show();
    }
}
