package main;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;

import static main.Main.annotationPath;

class LeftPain extends VBox {
    private TextField x_field = new TextField();
    private TextField y_field = new TextField();
    private TextField width_field = new TextField();
    private TextField height_field = new TextField();

    LeftPain() {
        HBox x_pos = new HBox();
        HBox y_pos = new HBox();
        HBox width_set = new HBox();
        HBox height_set = new HBox();

        x_pos.getChildren().addAll(new Label("    x    "), x_field);
        y_pos.getChildren().addAll(new Label("    y    "), y_field);
        width_set.getChildren().addAll(new Label("width"), width_field);
        height_set.getChildren().addAll(new Label("height"), height_field);

        RadioButton noActionButton = new RadioButton("0");
        RadioButton ActionButton = new RadioButton("1");
        ToggleGroup toggleGroup = new ToggleGroup();
        noActionButton.setToggleGroup(toggleGroup);
        noActionButton.setSelected(true);
        ActionButton.setToggleGroup(toggleGroup);

        HBox radio_box = new HBox();
        radio_box.getChildren().addAll(noActionButton, ActionButton);

        HBox start_set = new HBox();
        HBox end_set = new HBox();
        int textFieldSize = 40;
        TextField startFieldHour = new TextField("00");
        startFieldHour.setPrefWidth(textFieldSize);
        TextField startFieldMinute = new TextField("00");
        startFieldMinute.setPrefWidth(textFieldSize);
        TextField startFieldSecond = new TextField("00");
        startFieldSecond.setPrefWidth(textFieldSize);
        TextField endFieldHour = new TextField("00");
        endFieldHour.setPrefWidth(textFieldSize);
        TextField endFieldMinute = new TextField("00");
        endFieldMinute.setPrefWidth(textFieldSize);
        TextField endFieldSecond = new TextField("00");
        endFieldSecond.setPrefWidth(textFieldSize);
        start_set.getChildren().addAll(new Label("start"), startFieldHour, new Label(":"), startFieldMinute, new Label(":"), startFieldSecond);
        end_set.getChildren().addAll(new Label("  end  "), endFieldHour, new Label(":"), endFieldMinute, new Label(":"), endFieldSecond);

        Button pushButton = new Button("Push");
        pushButton.setOnAction(event -> {
            if (noActionButton.isSelected()) {
                System.out.println("0");
            } else {
                System.out.println("1");
            }
            try {
                File file = new File(annotationPath);
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                printWriter.println(x_field.getText() + " " + y_field.getText() + " " + width_field.getText() + " " + height_field.getText());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.getChildren().addAll(x_pos, y_pos, width_set, height_set, radio_box, start_set, end_set, pushButton);
    }

    void setParam(double x, double y, double width, double height) {
        x_field.setText(String.valueOf((int) x));
        y_field.setText(String.valueOf((int) y));
        width_field.setText(String.valueOf((int) width));
        height_field.setText(String.valueOf((int) height));
    }
}
