package main;

import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class AnnotationArea extends TextArea {
    AnnotationArea(String path) {
        setPrefSize(600, 400);
        setEditable(false);
        try {
            Scanner s = new Scanner(new File(path)).useDelimiter("\n");
            while (s.hasNext()) {
                if (s.hasNextInt()) { // check if next token is an int
                    this.appendText(s.nextInt() + "\n"); // display the found integer
                } else {
                    this.appendText(s.next() + "\n"); // else read the next token
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void reload(String path) {
        this.clear();
        try {
            Scanner s = new Scanner(new File(path)).useDelimiter("\n");
            while (s.hasNext()) {
                if (s.hasNextInt()) { // check if next token is an int
                    this.appendText(s.nextInt() + "\n"); // display the found integer
                } else {
                    this.appendText(s.next() + "\n"); // else read the next token
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
