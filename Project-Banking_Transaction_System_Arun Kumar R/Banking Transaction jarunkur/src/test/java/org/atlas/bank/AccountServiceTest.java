package org.atlas.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private DynamoDbClient mockClient;

    @BeforeEach
    void setUp() {
        mockClient = Mockito.mock(DynamoDbClient.class);
    }

    // ---------------- Deposit ----------------
    @Test
    void testDepositSuccess() {
        // Arrange
        String customerId = "C1";
        String accountId = "A123";
        double balanceBefore = 500;

        // Scanner inputs (AccountID, PIN, Amount)
        String input = accountId + "\n1111\n200\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Mock account check
        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n(String.valueOf(balanceBefore)).build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        // Act
        AccountService.deposit(mockClient, sc, customerId);

        // Assert (verify balance update was called)
        verify(mockClient, atLeastOnce()).updateItem(any(UpdateItemRequest.class));
        verify(mockClient, atLeastOnce()).putItem(any(PutItemRequest.class));
    }

    @Test
    void testDepositFailsWrongPin() {
        String customerId = "C1";
        String accountId = "A123";

        // Inputs: AccountID, wrong PIN, Amount
        String input = accountId + "\n9999\n200\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n("500").build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        AccountService.deposit(mockClient, sc, customerId);

        // No update should happen
        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    void testDepositFailsInvalidAmount() {
        String customerId = "C1";
        String accountId = "A123";

        String input = accountId + "\n1111\n0\n"; // 0 is invalid
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n("500").build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        AccountService.deposit(mockClient, sc, customerId);

        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    // ---------------- Withdraw ----------------
    @Test
    void testWithdrawSuccess() {
        String customerId = "C1";
        String accountId = "A123";

        String input = accountId + "\n1111\n200\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n("500").build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        AccountService.withdraw(mockClient, sc, customerId);

        verify(mockClient, atLeastOnce()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    void testWithdrawFailsInsufficientBalance() {
        String customerId = "C1";
        String accountId = "A123";

        String input = accountId + "\n1111\n600\n"; // exceeds balance
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n("500").build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        AccountService.withdraw(mockClient, sc, customerId);

        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }

    // ---------------- Transfer ----------------
    @Test
    void testTransferFailsDestinationNotFound() {
        String customerId = "C1";
        String sourceAccount = "A123";

        String input = sourceAccount + "\n1111\n1\nXYZ\n200\n";
        // SourceAcc, PIN, bankChoice=1, destAcc=XYZ (not found), amount
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(mockClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder()
                        .item(Map.of(
                                "CustomerID", AttributeValue.builder().s(customerId).build(),
                                "Balance", AttributeValue.builder().n("500").build(),
                                "TransferPinHash", AttributeValue.builder().s(PinHasher.hashPin("1111")).build()
                        ))
                        .build());

        // accountExists will return false
        when(mockClient.getItem(GetItemRequest.builder()
                .tableName("Account")
                .key(Map.of("AccountID", AttributeValue.builder().s("XYZ").build()))
                .build()))
                .thenReturn(GetItemResponse.builder().build());

        AccountService.transfer(mockClient, sc, customerId);

        verify(mockClient, never()).updateItem(any(UpdateItemRequest.class));
    }
}
