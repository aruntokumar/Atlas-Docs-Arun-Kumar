
package day07_java;

import java.util.LinkedList;

public class FruitListDemo1 {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();

        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        System.out.println("Before removals: " + fruits);

        String first = fruits.removeFirst();  // removes and returns "Apple"
        String last = fruits.removeLast();    // removes and returns "Date"

        System.out.println("Removed first: " + first);
        System.out.println("Removed last: " + last);
        System.out.println("After removals: " + fruits);
    }
}