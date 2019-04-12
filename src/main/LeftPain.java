package main;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;

import static main.Main.*;

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
            String actionValue;
            if (noActionButton.isSelected()) {
                actionValue = "0";
            } else {
                actionValue = "1";
            }
            try {
                long lineNumber = CountNumberOfTextLines(annotationPath);
                File file = new File(annotationPath);
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                printWriter.println((lineNumber - 1) + " " + openedVideo + " " + actionValue + " " + startFieldHour.getText() + ":" + startFieldMinute.getText() + ":" + startFieldSecond.getText() + " " + endFieldHour.getText() + ":" + endFieldMinute.getText() + ":" + endFieldSecond.getText() + " " + x_field.getText() + " " + y_field.getText() + " " + width_field.getText() + " " + height_field.getText());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.getChildren().addAll(annotationLabel, videoLabel, x_pos, y_pos, width_set, height_set, radio_box, start_set, end_set, pushButton);
    }

    void setParam(double x, double y, double width, double height) {
        x_field.setText(String.valueOf((int) x));
        y_field.setText(String.valueOf((int) y));
        width_field.setText(String.valueOf((int) width));
        height_field.setText(String.valueOf((int) height));
    }

    private long CountNumberOfTextLines(String filePath) {
        // カウントした行数を格納する整数型の変数を定義し、0で初期化する。
        long lineCount = 0;

        // 例外処理の始まり
        try {
            // ファイルを読み込みモードでオープンする。ファイルが存在しなかったりする場合に FileNotFoundException がスローされる。
            FileReader fr = new FileReader(filePath);

            // ファイルを読むための便利なクラス BufferedReader のオブジェクトを作る。
            BufferedReader br = new BufferedReader(fr);

            // 読み込んだ1行の文字列を格納するための変数を定義する。
            String line;

            // 1行目を読んでみる。もし、空のファイルなら、line には null がセットされる。
            line = br.readLine();

            // ファイルの最後まで来て null が返ってくるまで、処理を繰り返す。
            while (line != null) {
                // 1行読み込むに成功するたびに、行数のカウントを1増やす。
                lineCount++;

                // readLine メソッドを使ってもう1行読み込む。
                line = br.readLine();
            }

            // ストリームを閉じて、BufferedReader のリソースを開放する。
            // このとき、同時にFileReader のcloseも行われるので、fr.close() は必要ない。なので、ファイルもここで閉じられます。
            br.close();
        } catch (IOException e) {
            // 15行目でエラーが発生するとここに来る。
            e.printStackTrace();
        }// 18、24、38行目でエラーが発生するとここに来る。


        // カウントした行数を返す。
        return lineCount;
    }

}
