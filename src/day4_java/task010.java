package day4_java;

import java.util.stream.*;
class DoubleColonOp {
    public static void main(String[] args) {
        Stream<String> stream
                = Stream.of("Hello", "My",
                "name", "is",
                "Prasunamba",
                ".MK");

        stream.forEach(System.out::println);
    }
}