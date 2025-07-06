package day07_java;

import java.util.LinkedList;

public class FruitListDemo5 {
    public static void main(String args[]) {
        LinkedList<String> fruits = new LinkedList<>();
        LinkedList<String> ll2;

        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Mango");

        System.out.println("Original LinkedList: " + fruits);

        // clone() returns Object, so we must cast
        fruits = (LinkedList<String>) fruits.clone();

        System.out.println("Cloned LinkedList:   " + fruits);
    }
}