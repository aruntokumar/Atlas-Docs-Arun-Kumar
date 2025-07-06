package day5_java;

import java.util.stream.Stream;

public class task018 {
    public static void main(String[] args) {
        Stream<Integer> nums = Stream
                .iterate(1, n -> n + 1)
                .limit(20); // generates numbers from 1 to 20

        nums
                .limit(10) // display only first 10
                .forEach(System.out::println);
    }
}