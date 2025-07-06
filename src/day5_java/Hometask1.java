package day5_java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class hometask1 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("largefile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}