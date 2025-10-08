package org.atlas.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AllTests {

    private DynamoDbClient mockClient;
    private Scanner mockScanner;
    private String customerId = "C123";

    @BeforeEach
    void setUp() {
        mockClient = Mockito.mock(DynamoDbClient.class);
        mockScanner = Mockito.mock(Scanner.class);
    }

    // ---------------- AccountService Deposit Tests ----------------
    @Test
    void testDepositSuccess() {
        String accountId = "A100";
        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "500");

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(accountItem).build());

        assertDoesNotThrow(() -> AccountService.deposit(mockClient, mockScanner, customerId));
        verify(mockClient, atLeastOnce()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    void testDepositFailsWrongPin() {
        String accountId = "A123";
        when(mockScanner.nextLine()).thenReturn(accountId, "9999", "200");

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("500").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(accountItem).build());

        AccountService.deposit(mockClient, mockScanner, customerId);
        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    void testDepositNegativeAmount() {
        String accountId = "A300";
        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "-100");

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(accountItem).build());

        assertDoesNotThrow(() -> AccountService.deposit(mockClient, mockScanner, customerId));
        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    // ---------------- AccountService Withdraw Tests ----------------
    @Test
    void testWithdrawSuccess() {
        String accountId = "A101";
        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "200");

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(accountItem).build());

        assertDoesNotThrow(() -> AccountService.withdraw(mockClient, mockScanner, customerId));
        verify(mockClient, atLeastOnce()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    void testWithdrawFailsInsufficientBalance() {
        String accountId = "A102";
        when(mockScanner.nextLine()).thenReturn(accountId, "1234", "1500");

        Map<String, AttributeValue> accountItem = new HashMap<>();
        accountItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        accountItem.put("Balance", AttributeValue.builder().n("1000").build());
        accountItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(accountItem).build());

        AccountService.withdraw(mockClient, mockScanner, customerId);
        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    // ---------------- AccountService Transfer Tests ----------------
    @Test
    void testTransferFailsDestinationNotFound() {
        String sourceAccount = "A200";
        when(mockScanner.nextLine()).thenReturn(sourceAccount, "1234", "1", "XYZ", "200");

        Map<String, AttributeValue> sourceItem = new HashMap<>();
        sourceItem.put("CustomerID", AttributeValue.builder().s(customerId).build());
        sourceItem.put("Balance", AttributeValue.builder().n("1000").build());
        sourceItem.put("TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1234")).build());

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().item(sourceItem).build())
                .thenReturn(GetItemResponse.builder().build()); // Destination not found

        AccountService.transfer(mockClient, mockScanner, customerId);
        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    // ---------------- CustomerService Tests ----------------
    @Test
    void testRegisterCustomerSuccess() {
        when(mockScanner.nextLine()).thenReturn(
                "John Doe", "123 Main St", "9876543210", "john@gmail.com", "Password@1", "1234"
        );

        when(mockClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().items(List.of()).build());

        when(mockClient.putItem(any(PutItemRequest.class)))
                .thenReturn(PutItemResponse.builder().build());

        CustomerService.registerCustomer(mockClient, mockScanner);
        verify(mockClient, atLeastOnce()).putItem(any(PutItemRequest.class));
    }

    @Test
    void testRegisterCustomerDuplicateEmail() {
        Map<String, AttributeValue> existing = new HashMap<>();
        existing.put("Email", AttributeValue.builder().s("john@gmail.com").build());

        when(mockClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().items(List.of(existing)).build());

        when(mockScanner.nextLine()).thenReturn(
                "John Doe", "123 Main St", "9876543210", "john@gmail.com", "Password@1", "1234"
        );

        CustomerService.registerCustomer(mockClient, mockScanner);
        verify(mockClient, never()).putItem(any(PutItemRequest.class));
    }

    @Test
    void testLoginSuccess() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Email", AttributeValue.builder().s("john@gmail.com").build());
        item.put("Password", AttributeValue.builder().s("Password@1").build());
        item.put("CustomerID", AttributeValue.builder().s("C123").build());

        when(mockClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().items(List.of(item)).build());

        String id = CustomerService.login(mockClient, "john@gmail.com", "Password@1");
        assertEquals("C123", id);
    }

    @Test
    void testLoginWrongPassword() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Email", AttributeValue.builder().s("john@gmail.com").build());
        item.put("Password", AttributeValue.builder().s("Password@1").build());
        item.put("CustomerID", AttributeValue.builder().s("C123").build());

        when(mockClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().items(List.of(item)).build());

        String id = CustomerService.login(mockClient, "john@gmail.com", "WrongPass@1");
        assertNull(id);
    }

    @Test
    void testUpdatePassword() {
        when(mockClient.updateItem(any(UpdateItemRequest.class)))
                .thenReturn(UpdateItemResponse.builder().build());

        CustomerService.updatePassword(mockClient, "C123", "NewPassword@1");

        ArgumentCaptor<UpdateItemRequest> captor = ArgumentCaptor.forClass(UpdateItemRequest.class);
        verify(mockClient).updateItem(captor.capture());
        assertEquals("C123", captor.getValue().key().get("CustomerID").s());
    }

    // ---------------- PinHasher Tests ----------------
    @Test
    void testPinHasher() {
        String pin = "1234";
        String hash = PinHasher.hashPin(pin);
        assertTrue(PinHasher.verifyPin(pin, hash));
        assertFalse(PinHasher.verifyPin("4321", hash));
    }

    // ---------------- DynamoDbClientProvider Test ----------------
    @Test
    void testDynamoDbSingleton() {
        DynamoDbClient client1 = DynamoDbClientProvider.getClient();
        DynamoDbClient client2 = DynamoDbClientProvider.getClient();
        assertSame(client1, client2);
    }

    // ---------------- TransactionStackQueue Test ----------------
    @Test
    void testTransactionStackQueue() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TransactionStackQueue.addTransaction("Deposit 100");
        TransactionStackQueue.addTransaction("Withdraw 50");
        TransactionStackQueue.showTransactions();

        String output = outContent.toString();
        assertTrue(output.contains("Deposit 100"));
        assertTrue(output.contains("Withdraw 50"));
    }
}
