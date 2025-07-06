//Write a program to take input from the user and display it to the user

package day1_java;

import java.util.Scanner;
public class task007 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter your login id: ");
        String loginID = scanner.nextLine();
        System.out.print("enter your password: ");
        String password = scanner.nextLine();
        System.out.println("your login is" + loginID);
        System.out.println("password is : +" + password);
    }
}