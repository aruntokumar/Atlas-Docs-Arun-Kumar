/*Wap to check if  week days
1  ===> sunday
2 ===> monday
So on
8 and above ===> invalid input
*/
package day1_java;

import java.util.Scanner;
public class task011{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter a number :");
        int day = scanner.nextInt();
        switch(day)
        {
            case 1:
                System.out.println("sunday");
                break;
            case 2:
                System.out.println("monday");
                break;
            case 3:
                System.out.println("tuesday");
                break;
            case 4:
                System.out.println("wednesday");
                break;
            case 5:
                System.out.println("thursday");
                break;
            case 6:
                System.out.println("friday");
                break;
            case 7:
                System.out.println("saturday");
                break;
            default:
                System.out.println("invalid input");
        }

    }
}