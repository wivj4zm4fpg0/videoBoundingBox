package main;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class RightPain extends BorderPane {
    RightPain(ObservableList<String> listRecord) {
        HBox right_bottom_buttons = new HBox();
        right_bottom_buttons.getChildren().addAll(new javafx.scene.control.Button("next"), new javafx.scene.control.Button("back"));

        ListView<String> listView = new ListView<>();
        listView.setItems(listRecord);
        listView.setOnMouseClicked(event -> System.out.println(listView.getSelectionModel().getSelectedItem()));

        this.setCenter(listView);
        this.setBottom(right_bottom_buttons);
    }
}
