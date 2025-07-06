package day07_java;

import java.util.Arrays;
import java.util.LinkedList;

public class FruitListDemo4 {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        // Convert to Object[] array
        Object[] a = fruits.toArray();

        System.out.println("Array elements:");
        System.out.println(Arrays.toString(a)); // prints the whole array
    }
}