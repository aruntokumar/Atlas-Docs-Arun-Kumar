package day08_java;
import java.util.Stack;

public class stack3 {
    public static void main(String[] args) {
        Stack<Integer> names = new Stack<>();
        names.push(10);
        names.push(20);
        names.push(30);
        names.push(40);
        int searchName = 40;
        int position = names.search(searchName);
        System.out.println("20 is in "  +  position);
    }
}