package day6_java;

import java.util.Scanner;

public class task002 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        char[] arr = name.toCharArray();
        String reversed = "";
        for (int i = arr.length - 1; i >= 0; i--) {
            reversed += arr[i];
        }

        System.out.println("Reversed name: " + reversed);
        scanner.close();
    }
}