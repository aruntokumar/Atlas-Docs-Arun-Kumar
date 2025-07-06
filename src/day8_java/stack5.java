package day08_java;
import java.util.Stack;


public class stack5 {
    public static void main(String[] args) {
        // Create a stack of integers
        Stack<Integer> stack = new Stack<>();

        // Push elements onto the stack
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        boolean result = stack.empty();
        System.out.println("check list is empty or not : " + result);

    }
}