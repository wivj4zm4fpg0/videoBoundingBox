package main;

import java.io.IOException;

public class Cmd {
    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("powershell /c sed '$d' test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
