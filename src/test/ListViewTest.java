package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ListViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox hBox = new HBox();
        Button nextButton = new Button("next");
        Button backButton = new Button("back");
        hBox.getChildren().addAll(nextButton, backButton);

        nextButton.setOnAction(event -> {

        });

        ListView<String> listView = new ListView<>();
        ObservableList<String> listRecord = FXCollections.observableArrayList();
        listView.setItems(listRecord);
        listView.setOnMouseClicked(event -> System.out.println(listView.getSelectionModel().getSelectedItem()));

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");

        menuBar.getMenus().addAll(menuFile);

        MenuItem menuItem = new MenuItem("ファイルを開く");

        menuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                System.out.println(file.getPath());
            }
        });

        MenuItem dirItem = new MenuItem("ディレクトリを開く");
        dirItem.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("ディレクトリ選択");
            File file = directoryChooser.showDialog(primaryStage);
            if (file != null) {
                listRecord.clear();
                File[] files = new File(file.getPath()).listFiles();
                assert files != null;

                for (File entry : files) {
                    if (entry.isFile() && entry.getPath().endsWith(".mp4")) {
                        listRecord.add(entry.getName());
                    }
                }
            }
        });
        menuFile.getItems().addAll(menuItem, dirItem);

        primaryStage.setTitle("ListView_Test");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(listView);
        borderPane.setBottom(hBox);
        primaryStage.setScene(new Scene(borderPane, 640, 480));
        primaryStage.show();
    }
}
