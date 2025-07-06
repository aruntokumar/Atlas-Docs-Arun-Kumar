package day5_java;

import java.util.*;
import java.util.stream.Collectors;

public class task015 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);

        List<Integer> squareOfNums = numbers.stream()
                .map(num -> num * num)
                .collect(Collectors.toList());

        System.out.println("Original numbers: " + numbers);
        System.out.println("Squares: " + squareOfNums);
    }
}