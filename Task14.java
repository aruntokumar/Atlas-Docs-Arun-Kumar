package Arrays;

public class ArrayExample {
    public static void main(String[] args) {
        int[] arr = {100, 250, 350, 450, 550};

        // Print all elements
        System.out.println("Array elements:");
        for (int i = 0; i < arr.length; i++) {
            System.out.println("arr[" + i + "] = " + arr[i]);
        }


        System.out.println("\nAccessing arr[5]:");
        System.out.println(arr[5]);


        System.out.println("\nAccessing arr[-1]:");
        System.out.println(arr[-1]);
    }
}
