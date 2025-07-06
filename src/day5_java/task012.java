package day5_java;

import java.util.ArrayList;

public class task012 {
    public static void main(String[] args) {
        ArrayList<String> friends = new ArrayList<>();

        friends.add("Alice");
        friends.add("Bob");
        friends.add("Charlie");
        friends.add("David");
        friends.add("Emma");

        System.out.println("My Friends:");
        for (String name : friends) {
            System.out.println(name);
        }
    }
}