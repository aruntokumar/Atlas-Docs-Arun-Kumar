package day5_java;

import java.io.*;

public class task007 {
    public static void main(String[] args) {
        FileInputStream file1 = null;
        FileInputStream file2 = null;
        SequenceInputStream file3 = null;
        BufferedInputStream br1 = null;
        BufferedOutputStream br2 = null;

        try {
            file1 = new FileInputStream("NewFile01.txt");
            file2 = new FileInputStream("NewFile02.txt");

            file3 = new SequenceInputStream(file1, file2);

            br1 = new BufferedInputStream(file3);
            br2 = new BufferedOutputStream(new FileOutputStream("MergedFile.txt"));  // ✅ Output to new file

            int ch;
            while ((ch = br1.read()) != -1) {
                br2.write(ch);
            }

            System.out.println("Merged both files successfully into MergedFile.txt");

        } catch (IOException e) {
            System.out.println("Sorry..!! File Not Found or IO Error...!!!");
        } finally {
            try {
                if (br1 != null) br1.close();
                if (br2 != null) br2.close();
                if (file1 != null) file1.close();
                if (file2 != null) file2.close();
            } catch (IOException e) {
                System.out.println("Error closing resources.");
            }
        }
    }
}