package day5_java;

import java.io.*;

public class task006 {
    public static void main(String args[]) {
        FileInputStream infile = null;
        FileOutputStream outfile = null;

        try {
            infile = new FileInputStream("NewFile01.txt");
            outfile = new FileOutputStream("NewFile05.txt");

            int byteread;

            while ((byteread = infile.read()) != -1) {
                outfile.write(byteread);
            }

            System.out.println("Bytes copied from NewFile01.txt to NewFile05.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Sorry..!! File Not Found...!!!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (infile != null) infile.close();
                if (outfile != null) outfile.close();
            } catch (IOException e) {
                System.out.println("Error closing the files.");
            }
        }
    }
}