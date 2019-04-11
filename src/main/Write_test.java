package main;

import java.io.*;

public class Write_test {
    public static void main(String[] args) {
        try {
            File file = new File("test.txt");
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            printWriter.println("Hello World!!");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
