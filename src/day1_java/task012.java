/*Wap to check loginid and password validation
Hint use while loop */
package day1_java;

import java.util.Scanner;

public class task012 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String correctLoginId = "Shireesha";
        String correctPwd = "12345867";
        int count = 0;

        while (true) {
            System.out.println("Enter your login ID:");
            String loginId = sc.nextLine();
            System.out.println("Enter your password:");
            String pwd = sc.nextLine();
            if (loginId.equals(correctLoginId) && pwd.equals(correctPwd)) {
                count++;
                System.out.println("You have logged in " + count + " times.");
            } else {
                System.out.println("Invalid login ID or password.");
            }

            System.out.println("Do you want to try again? (yes/no):");
            String response = sc.nextLine();
            if (!response.equalsIgnoreCase("yes"))
            {
                break;
            }
        }
        System.out.println("Thank you for using the login system.");
    }
}