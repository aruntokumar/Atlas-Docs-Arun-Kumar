package day5_java;

import java.io.*;  // ✅ Correct import statement

public class task001 {
    public static void main(String args[]) {
        File f1 = new File("FileName01.txt");  // ✅ Use straight double quotes

        FileOutputStream outfile = null;

        // ✅ Corrected character literals and enclosed in single quotes
        byte Text[] = {'I', ' ', 'L', 'O', 'V', 'E', ' ', 'I', 'N', 'D', 'I', 'A'};

        try {
            outfile = new FileOutputStream(f1);  // ✅ FileOutputStream to write bytes
            outfile.write(Text);                // ✅ Writing byte array to file
        } catch (IOException e) {
            System.out.println(e);              // ✅ Error handling
            System.exit(-1);
        } finally {
            try {
                if (outfile != null)
                    outfile.close();            // ✅ Close the stream
            } catch (IOException e) {
                System.out.println("Error while closing the file: " + e);
            }
        }

        System.out.println("Write Byte");
        System.out.println("Thank You...!!!");
    }
}