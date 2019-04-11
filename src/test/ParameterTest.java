package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParameterTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        HBox x_pos = new HBox();
        HBox y_pos = new HBox();
        HBox width_set = new HBox();
        HBox height_set = new HBox();
        TextField x_field = new TextField();
        TextField y_field = new TextField();
        TextField width_field = new TextField();
        TextField height_field = new TextField();
        x_pos.getChildren().addAll(new Label("    x    "), x_field);
        y_pos.getChildren().addAll(new Label("    y    "), y_field);
        width_set.getChildren().addAll(new Label("width"), width_field);
        height_set.getChildren().addAll(new Label("height"), height_field);
        RadioButton radioButton = new RadioButton("0");
        RadioButton radioButton1 = new RadioButton("1");
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setSelected(true);
        radioButton1.setToggleGroup(toggleGroup);
        HBox radio_box = new HBox();
        radio_box.getChildren().addAll(radioButton, radioButton1);
        root.getChildren().addAll(x_pos, y_pos, width_set, height_set, radio_box);
        primaryStage.setTitle("parameter_test");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }
}
