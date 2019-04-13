package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClearTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Button button = new Button("click");
        Button button1 = new Button("click1");
        pane.getChildren().addAll(button);

        button.setOnAction(event -> {
            pane.getChildren().clear();
            pane.getChildren().addAll(button1);
        });

        primaryStage.setScene(new Scene(pane, 400, 400));
        primaryStage.show();
    }
}
