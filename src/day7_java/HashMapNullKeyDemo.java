package day07_java;

import java.util.*;

public class HashMapNullKeyDemo {
    public static void main(String[] args) {
        Map<String, Integer> hm = new HashMap<>();

        // One null key allowed
        hm.put(null, 100);
        hm.put("A", 1);
        hm.put("B", 2);

        System.out.println("HashMap with one null key: " + hm);
    }
}