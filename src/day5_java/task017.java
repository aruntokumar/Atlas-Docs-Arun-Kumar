package day5_java;

import java.util.*;
import java.util.stream.Collectors;

public class task017
{
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 20, 10, 30, 20, 40, 50, 30);

        List<Integer> removeDups = numbers.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Original list: " + numbers);
        System.out.println("List after removing duplicates: " + removeDups);
    }
}