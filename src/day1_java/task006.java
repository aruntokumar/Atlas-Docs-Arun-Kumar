//Write a program to check if a is greater or b Use ternary op
package day1_java;

public class task006 {
    public static void main(String[] args) {
        int a = 10;
        int b = 15;
        String result = (a > b) ? "a is greater" : (a < b) ? "b is greater" : "a and b are equal";
        System.out.println(result);
    }
}