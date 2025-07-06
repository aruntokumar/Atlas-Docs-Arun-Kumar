package day08_java;

import java.util.Stack;

public class stack4 {
    public static void main(String[] args) {
        Stack<Object> stack = new Stack<>();

        stack.push("Prasunamba");
        stack.push("Meher");
        stack.push(3);
        Object top = stack.peek();
        boolean result = stack.empty();
        System.out.println("Top element (peek): " + top);
        System.out.println("Stack after peek(): " + stack);
        System.out.println(result);
    }
}