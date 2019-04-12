package main;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import static main.Main.*;

class RightPain extends BorderPane {
    RightPain(CenterPain centerPain, ObservableList<String> listRecord) {
        ListView<String> listView = new ListView<>();
        listView.setItems(listRecord);
        listView.setOnMouseClicked(event -> {
            openedVideo = listView.getSelectionModel().getSelectedItem();
            centerPain.readVideo(dirPath + "\\" + openedVideo);
            videoLabel.setText(openedVideo);
        });

        HBox right_bottom_buttons = new HBox();
        Button nextButton = new Button("next");
        Button backButton = new Button("back");
        right_bottom_buttons.getChildren().addAll(nextButton, backButton);

        nextButton.setOnAction(event -> {
            int nextIndex = listView.getFocusModel().getFocusedIndex() + 1;
            if (nextIndex != listView.getItems().size()) {
                centerPain.readVideo(dirPath + "\\" + listView.getItems().get(nextIndex));
                listView.getSelectionModel().select(nextIndex);
                videoLabel.setText(listView.getSelectionModel().getSelectedItem());
            }
        });

        backButton.setOnAction(event -> {
            int backIndex = listView.getFocusModel().getFocusedIndex() - 1;
            if (backIndex != -1) {
                centerPain.readVideo(dirPath + "\\" + listView.getItems().get(backIndex));
                listView.getSelectionModel().select(backIndex);
                videoLabel.setText(listView.getSelectionModel().getSelectedItem());
            }
        });

        this.setCenter(listView);
        this.setBottom(right_bottom_buttons);
    }
}
