package org.atlas.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.atlas.bank.PinHasher;

public class TransactionTest {

    private DynamoDbClient mockClient;
    private Scanner mockScanner;
    private String customerId = "C123";

    @BeforeEach
    void setup() {
        mockClient = mock(DynamoDbClient.class);
        mockScanner = mock(Scanner.class);
    }

    // ---------------- Deposit Test ----------------
    @Test
    void testDepositSuccess() {
        String accountId = "A100";

        // Mock Scanner input
        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "500"); // AccountID, PIN, Amount

        // Mock DynamoDb responses
        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        GetItemResponse response = GetItemResponse.builder().item(accountItem).build();
        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        assertDoesNotThrow(() -> AccountService.deposit(mockClient, mockScanner, customerId));
    }

    // ---------------- Withdraw Test ----------------
    @Test
    void testWithdrawSuccess() {
        String accountId = "A101";

        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "200"); // AccountID, PIN, Amount

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        GetItemResponse response = GetItemResponse.builder().item(accountItem).build();
        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        assertDoesNotThrow(() -> AccountService.withdraw(mockClient, mockScanner, customerId));
    }

    // ---------------- Transfer Test ----------------
    @Test
    void testTransferSuccess() {
        String sourceAccount = "A200";
        String destAccount = "A201";

        when(mockScanner.nextLine()).thenReturn(
                sourceAccount, "1234", "3", // Source AccountID, PIN, Bank Choice
                destAccount, "300"           // Destination AccountID, Amount
        );

        Map<String, AttributeValue> sourceItem = new HashMap<>();
        sourceItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        sourceItem.put("Balance", AttributeValue.builder().n("1000").build());
        sourceItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        Map<String, AttributeValue> destItem = new HashMap<>();
        destItem.put("Balance", AttributeValue.builder().n("500").build());

        // Return source account first, then destination
        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(sourceItem).build())
                .thenReturn(GetItemResponse.builder().item(destItem).build());

        assertDoesNotThrow(() -> AccountService.transfer(mockClient, mockScanner, customerId));
    }

    // ---------------- Negative Deposit Test ----------------
    @Test
    void testDepositNegativeAmount() {
        String accountId = "A300";

        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "-100"); // Negative deposit

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().item(accountItem).build());

        assertDoesNotThrow(() -> AccountService.deposit(mockClient, mockScanner, customerId));
    }

    // ---------------- Negative Withdraw Test ----------------
    @Test
    void testWithdrawNegativeAmount() {
        String accountId = "A301";

        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "-50"); // Negative withdrawal

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().item(accountItem).build());

        assertDoesNotThrow(() -> AccountService.withdraw(mockClient, mockScanner, customerId));
    }
}
