package day07_java;

import java.util.LinkedList;

public class FruitListDemo6 {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        System.out.println("Initial list: " + fruits);
        fruits.push("Apricot");
        System.out.println("After push('Apricot'): " + fruits);
        String popped = fruits.pop();
        System.out.println("Popped element: " + popped);
        System.out.println("After pop(): " + fruits);
    }
}