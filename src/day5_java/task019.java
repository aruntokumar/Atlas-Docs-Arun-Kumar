package day5_java;

import java.util.stream.Stream;

public class task019 {
    public static void main(String[] args) {
        Stream<Integer> nums = Stream
                .iterate(1, n -> n + 1)
                .limit(20);

        Stream<Integer> skipNums = nums.skip(15);

        skipNums.forEach(System.out::println);
    }
}