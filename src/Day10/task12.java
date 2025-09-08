import java.util.stream.*;
class task012 {
    public static void main(String[] args) {
        Stream<String> stream
                = Stream.of("Hello", " My",
                " name", " is",
                " Pratheesh",
                ".K");

        stream.forEach(System.out::println);
        //System.out.println("println method");
    }
}