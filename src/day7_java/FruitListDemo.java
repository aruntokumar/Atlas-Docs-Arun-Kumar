package day07_java;

import java.util.LinkedList;

public class FruitListDemo {
    public static void main(String[] args) {
        // Define the LinkedList
        LinkedList<String> fruits = new LinkedList<>();

        // Add elements using add()
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        // Add at a specific position
        fruits.add(2, "Blueberry"); // Insert "Blueberry" at index 2

        // Add first and add last explicitly
        fruits.addFirst("Apricot");
        fruits.addLast("Elderberry");

        // Display the list
        System.out.println("Fruits list: " + fruits);

        // Access elements
        System.out.println("First fruit: " + fruits.getFirst());
        System.out.println("Last fruit: " + fruits.getLast());

        // Remove elements
        fruits.remove("Date");
        fruits.removeFirst();
        fruits.removeLast();

        // Final list state
        System.out.println("After removals: " + fruits);
    }
}