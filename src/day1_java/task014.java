package day1_java;
import java.util.Arrays;  // Import for Arrays.toString()

public class task014 {

    public static void main(String[] args) {
        // Initialize char array and print it
        char[] arr = {'a', 'e', 'i', 'o', 'u'};
        System.out.println(arr);  // Outputs: aeiou

        // Initialize String array
        String[] names = {"Meena", "Tina", "Veena", "heena"};
        System.out.println(names[0]);  // Outputs: Meena

        // Modify an element
        names[1] = "Reena";
        System.out.println(names[1]);  // Outputs: Reena

        // Print the array length
        System.out.println("Length of names array: " + names.length);  // Outputs: 4

        // Safely access the last element instead of the invalid index 4
        System.out.println("Last element in names: " + names[names.length - 1]);  // Outputs: heena

        // Optionally, print the entire array in a neat format
        System.out.println("All names: " + Arrays.toString(names));
    }
}