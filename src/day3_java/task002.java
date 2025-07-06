
package day3_java;

public class task002 {

    public static void main(String[] args) {

        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);

        } catch (Exception e) {
            // Catch block with the corrected variable 'e'
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }

}
