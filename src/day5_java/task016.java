package day5_java;

import java.util.*;
import java.util.stream.Collectors;

public class task016 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(11, 22, 33, 44, 55);

        List<Integer> oddNumbers = numbers.stream()
                .filter(num -> num % 2 != 0)
                .collect(Collectors.toList());

        System.out.println("Original numbers: " + numbers);
        System.out.println("Odd numbers: " + oddNumbers);
    }
}