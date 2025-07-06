package day5_java;

import java.util.ArrayList;
import java.util.List;

public class task014 {

    public static List<String> getPlaces() {
        List<String> places = new ArrayList<>();
        places.add("Nepal, Kathmandu");
        places.add("Nepal, Pokhara");
        places.add("India, Delhi");
        places.add("USA, New York");
        places.add("Africa, Nigeria");
        return places;
    }

    public static void main(String[] args) {
        List<String> myPlaces = getPlaces();
        System.out.println("Places from Nepal:");

        myPlaces.stream()
                .filter(p -> p.startsWith("Nepal"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }
}