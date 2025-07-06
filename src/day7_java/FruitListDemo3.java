
package day07_java;
import java.util.LinkedList;

public class FruitListDemo3 {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        System.out.println("Before update: " + fruits);

        // Replace the element at index 1 ("Banana") with "Blueberry"
        String previous = fruits.set(1, "Blueberry");

        System.out.println("Replaced \"" + previous + "\" at index 1");
        System.out.println("After update: " + fruits);

        // --- 1. Using for-loop with get() ---
        System.out.println("\nDisplaying using for-loop with get():");
        for (int i = 0; i < fruits.size(); i++) {
            System.out.println("Index " + i + ": " + fruits.get(i));
        }

        // --- 2. Using enhanced for-each loop ---
        System.out.println("\nDisplaying using enhanced for-each loop:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }
}