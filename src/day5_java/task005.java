package day5_java;

import java.io.*;

public class task005 {
    public static void main(String args[]) {
        FileReader fr = null;

        try {
            fr = new FileReader("FileName03.txt");  // Open the file
            int ch;

            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);        // Print each character
            }

            System.out.println("\nReading complete");
        } catch (FileNotFoundException e) {
            System.out.println("Sorry..!! File Not Found...!!!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fr != null)
                    fr.close();                     // Safely close FileReader
            } catch (IOException e) {
                System.out.println("Error closing file.");
            }
        }
    }
}