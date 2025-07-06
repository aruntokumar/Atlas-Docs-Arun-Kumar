package day3_java;

public class task004 {

    public static void main(String[] args) {

        try {
            int[] myNumbers = {1, 2, 3};

            // Accessing the valid index 2
            System.out.println(myNumbers[2]);

        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } finally {
            System.out.println("I'm from finally block.");
        }

    }

}