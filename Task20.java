public class NameArray {
    public static void main(String[] args) {
        // Initialize array with characters of the name
        char[] Name = {'A', 'r', 'u', 'n'};

        // Printing the array reference (not the contents)
        System.out.println(Name);  // This will print the name because char[] prints as string

        int n = Name.length;
        System.out.println("There are " + n + " letters in my name");

        // Traversing array using for loop
        for (int i = 0; i < n; i++) {
            System.out.print(Name[i] + " ");
        }
        System.out.println();  // newline

        // SHALLOW COPY
        char[] shallowCopy = Name; // both arrays refer to the same object
        shallowCopy[0] = 'X';  // modifying shallowCopy modifies Name too
        System.out.print("After shallow copy change, Name: ");
        for (int i = 0; i < n; i++) {
            System.out.print(Name[i] + " ");
        }
        System.out.println();

        // DEEP COPY
        char[] deepCopy = new char[n];
        for (int i = 0; i < n; i++) {
            deepCopy[i] = Name[i];
        }
        deepCopy[0] = 'P';  // revert change only in deepCopy
        System.out.print("Deep copy array: ");
        for (int i = 0; i < n; i++) {
            System.out.print(deepCopy[i] + " ");
        }
        System.out.println();
    }
}
