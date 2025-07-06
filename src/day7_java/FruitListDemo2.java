package day07_java;

import java.util.LinkedList;

public class FruitListDemo2 {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        System.out.println("Before update: " + fruits);

        String previous = fruits.set(1, "Blueberry");

        System.out.println("Replaced \"" + previous + "\" at index 1");
        System.out.println("After update: " + fruits);
    }
}