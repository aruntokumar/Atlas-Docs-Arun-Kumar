package day6_java;

import java.util.*;

public class hometask {
    public static int[] mergeAndSort(List<Integer> listA, List<Integer> listB) {
        int totalSize = listA.size() + listB.size();
        int[] merged = new int[totalSize];
        int index = 0;

        // Copy elements from both lists
        for (int x : listA) merged[index++] = x;
        for (int x : listB) merged[index++] = x;

        // Sort the merged array
        Arrays.sort(merged);
        return merged;
    }

    public static void main(String[] args) {
        List<Integer> listA = Arrays.asList(10, 5, 15);
        List<Integer> listB = Arrays.asList(20, 3, 2, 12);

        int[] result = mergeAndSort(listA, listB);

        System.out.println("Merged & Sorted Array: " + Arrays.toString(result));
    }
}