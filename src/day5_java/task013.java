package day5_java;

import java.util.*;

public class task013 {
    static List<String> fullName = new ArrayList<>();

    public static void main(String[] args) {
        fullName.add("Alice Johnson");
        fullName.add("Bob Smith");
        fullName.add("Charlie Brown");
        fullName.add("David Miller");
        fullName.add("Emma Watson");

        System.out.println("Friends' Full Names:");
        for (String name : fullName) {
            System.out.println(name);
        }
    }
}