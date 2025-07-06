package day5_java;

@FunctionalInterface
interface MyInterface {
    double getPiValue();
}

public class task009 {
    public static void main(String[] args) {
        MyInterface ref;
        ref = () -> 3.1415;
        System.out.println("Value of Pi = " + ref.getPiValue());
    }
}