package day1_java;

public class ElementDemo {
    public static void main(String[] args) {
        System.out.println("Find by label 'Helium': " + Element.valueOfLabel("Helium"));
        System.out.println("Find by atomic number 10: " + Element.valueOfAtomicNumber(10));
        System.out.println("Find by atomic weight 1.008f: " + Element.valueOfAtomicWeight(1.008f));
    }
}