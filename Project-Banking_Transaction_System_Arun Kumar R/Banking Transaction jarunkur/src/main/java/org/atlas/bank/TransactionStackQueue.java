package org.atlas.bank;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TransactionStackQueue {
    private static final Stack<String> transactionStack = new Stack<>();
    private static final Queue<String> transactionQueue = new LinkedList<>();

    public static void addTransaction(String txn) {
        transactionStack.push(txn);
        transactionQueue.offer(txn);
    }

    public static void showTransactions() {
        System.out.println("\n--- Transactions in Stack order ---");
        transactionStack.forEach(System.out::println);
        System.out.println("\n--- Transactions in Queue order ---");
        transactionQueue.forEach(System.out::println);
    }
}
