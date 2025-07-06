package day2_java;

public class task014 {

    // Overloaded add method: int + float
    void add(int x, float y) {
        System.out.println("add(int, float) called with x = " + x + ", y = " + y);
        System.out.println("Result: " + (x + y));
    }

    // Overloaded add method: float + int
    void add(float x, int y) {
        System.out.println("add(float, int) called with x = " + x + ", y = " + y);
        System.out.println("Result: " + (x + y));
    }

    public static void main(String[] args) {
        task014 calc = new task014();

        calc.add(10.50f, 60);   // calls add(float, int)
        calc.add(100, 80.80f);  // calls add(int, float)
    }
}