package day1_java;

public class task020 {
    public static void main(String[] args) {
        // Replace with your own name letters
        char[] Name = {'A', 'l', 'i', 'c', 'e'};

        // Print the array (as a String)
        System.out.println(Name);

        // Determine the length of the name
        int n = Name.length;
        System.out.println("There are " + n + " letters in my name");

        // Print each letter using a classic for loop
        System.out.print("Classic for loop: ");
        for (int i = 0; i < n; i++) {
            System.out.print(Name[i] + " ");
        }
        System.out.println();

        // Print each letter using enhanced for loop
        System.out.print("Enhanced for-each loop: ");
        for (char ch : Name) {
            System.out.print(ch + " ");
        }
        System.out.println();
    }
}