package main;

import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

class MainMenuBar extends MenuBar {
    MainMenuBar(Stage primaryStage, ObservableList<String> listRecord) {
        javafx.scene.control.Menu menuFile = new javafx.scene.control.Menu("File");

        this.getMenus().addAll(menuFile);

        javafx.scene.control.MenuItem menuItem = new javafx.scene.control.MenuItem("ファイルを開く");

        menuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                System.out.println(file.getPath());
            }
        });

        javafx.scene.control.MenuItem dirItem = new javafx.scene.control.MenuItem("ディレクトリを開く");
        dirItem.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("ディレクトリ選択");
            File file = directoryChooser.showDialog(primaryStage);
            if (file != null) {
                listRecord.clear();
                File[] files = new File(file.getPath()).listFiles();
                if (files != null) {
                    for (File entry : files) {
                        if (entry.isFile() && entry.getPath().endsWith(".mp4")) {
                            listRecord.add(entry.getName());
                        }
                    }
                }
            }
        });
        menuFile.getItems().addAll(menuItem, dirItem);

    }
}
