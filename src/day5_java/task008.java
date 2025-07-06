package day5_java;

import java.io.*;

public class task008 {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Usage: java FileRenameDemo <old_filename> <new_filename>");
            return;
        }

        File f1 = new File(args[0]);  // Old file
        File f2 = new File(args[1]);  // New file name

        if (!f1.exists()) {
            System.out.println("The file " + args[0] + " does not exist.");
            return;
        }

        if (f1.renameTo(f2)) {
            System.out.println("Renamed file " + f1.getName() + " to " + f2.getName() + " successfully.");
        } else {
            System.out.println("Failed to rename the file.");
        }
    }
}