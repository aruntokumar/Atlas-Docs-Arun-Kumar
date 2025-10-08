package org.atlas.bank;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionStackQueueTest {

    @Test
    void testAddTransactionAndShowTransactions() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TransactionStackQueue.addTransaction("Deposit 100");
        TransactionStackQueue.addTransaction("Withdraw 50");
        TransactionStackQueue.showTransactions();

        String output = outContent.toString();

        assertTrue(output.contains("Deposit 100"));
        assertTrue(output.contains("Withdraw 50"));
        assertTrue(output.contains("--- Transactions in Stack order ---"));
        assertTrue(output.contains("--- Transactions in Queue order ---"));
    }
}
