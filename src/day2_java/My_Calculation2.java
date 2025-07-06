package day2_java;

public class My_Calculation2 extends Calculation {
    public void multiplication(int x, int y) {
        z = x * y;
        System.out.println("The product of the given numbers: " + z);
    }

    public static void main(String[] args) {
        int a = 20, b = 10;
        My_Calculation2 demo = new My_Calculation2();
        demo.addition(a, b);       // inherited
        demo.subtraction(a, b);    // inherited
        demo.multiplication(a, b); // own method
    }
}