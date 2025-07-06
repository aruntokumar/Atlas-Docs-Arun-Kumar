//Wap to check the greater of 2 numbers

package day1_java;

import java.util.Scanner;
public class task009 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter the first number: ");
        int num1 = scanner.nextInt();
        System.out.print("enter the second number: ");
        int num2 = scanner.nextInt();
        if(num1 > num2)

        {
            System.out.println("num1 is greater :" + num1);
        }
        else

        {
            System.out.println("num2 is greater :" + num2);
        }
    }
}