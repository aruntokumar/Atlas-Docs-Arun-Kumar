package day5_java;

import java.io.*;

public class task002 {
    public static void main(String[] args) {
        FileInputStream infile = null;
        int b;

        try {
            infile = new FileInputStream("FileName01.txt"); // Open file for reading

            while ((b = infile.read()) != -1) {
                System.out.print((char) b); // Print characters without newline
            }

        } catch (IOException e) {
            System.out.println("Sorry..!! File Not Found...!!!");
        } finally {
            try {
                if (infile != null) {
                    infile.close(); // Always close file to free resources
                }
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }
        }
    }
}