package day5_java;

import java.io.FileReader;
import java.io.IOException;

public class hometask2 {
    public static void main(String[] args) {
        try (FileReader fr = new FileReader("largefile.txt")) {
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}