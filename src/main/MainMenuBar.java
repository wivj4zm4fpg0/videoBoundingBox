package main;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static main.Main.annotationPath;
import static main.Main.dirPath;

class MainMenuBar extends MenuBar {
    MainMenuBar(Stage primaryStage, ObservableList<String> listRecord) {
        Menu menuFile = new Menu("ファイル");

        this.getMenus().addAll(menuFile);

        MenuItem menuItem = new MenuItem("ファイルを開く");

        menuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null && file.getPath().endsWith(".txt")) {
                System.out.println(file.getPath());
                annotationPath = file.getPath();
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
                if (files != null) {
                    dirPath = files[0].getParent();
                    for (File entry : files) {
                        if (entry.isFile() && entry.getPath().endsWith(".mp4")) {
                            listRecord.add(entry.getName());
                        }
                    }
                }
            }
        });

        MenuItem exitItem = new MenuItem("終了");
        exitItem.setOnAction(event -> System.exit(0));

        menuFile.getItems().addAll(menuItem, dirItem, exitItem);
    }
}
