package org.atlas.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private DynamoDbClient mockClient;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockClient = mock(DynamoDbClient.class);
        mockScanner = mock(Scanner.class);
    }

    @Test
    void testRegisterCustomerSuccess() {
        // Mock Scanner inputs
        when(mockScanner.nextLine()).thenReturn(
                "John Doe",               // Name
                "123 Main St",            // Address
                "9876543210",             // Contact
                "john@gmail.com",         // Email
                "Password@1",             // Password
                "1234"                    // Transfer PIN
        );

        // Mock scan to return empty list → no duplicates
        ScanResponse scanResponse = ScanResponse.builder().items(List.of()).build();
        when(mockClient.scan(any(ScanRequest.class))).thenReturn(scanResponse);

        // Mock putItem to return a valid response
        PutItemResponse putResponse = PutItemResponse.builder().build();
        when(mockClient.putItem(any(PutItemRequest.class))).thenReturn(putResponse);

        // Mock account creation call
//        doNothing().when(mockClient).updateItem(any(UpdateItemRequest.class)); // for PIN set

        CustomerService.registerCustomer(mockClient, mockScanner);

        // Verify putItem called at least once for Customer table
        verify(mockClient, atLeastOnce()).putItem(any(PutItemRequest.class));
    }

    @Test
    void testRegisterCustomerDuplicateEmail() {
        Map<String, AttributeValue> existing = new HashMap<>();
        existing.put("Email", AttributeValue.builder().s("john@gmail.com").build());
        existing.put("Contact", AttributeValue.builder().s("9876543210").build());
        existing.put("Name", AttributeValue.builder().s("John Doe").build());
        existing.put("Address", AttributeValue.builder().s("123 Main St").build());
        existing.put("Password", AttributeValue.builder().s("Password@1").build());

        ScanResponse scanResponse = ScanResponse.builder().items(List.of(existing)).build();
        when(mockClient.scan(any(ScanRequest.class))).thenReturn(scanResponse);

        when(mockScanner.nextLine()).thenReturn(
                "John Doe",
                "123 Main St",
                "9876543210",
                "john@gmail.com",
                "Password@1",
                "1234"
        );

        CustomerService.registerCustomer(mockClient, mockScanner);

        // Should NOT call putItem due to duplicate
        verify(mockClient, never()).putItem(any(PutItemRequest.class));
    }

    @Test
    void testLoginSuccess() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Email", AttributeValue.builder().s("john@gmail.com").build());
        item.put("Password", AttributeValue.builder().s("Password@1").build());
        item.put("CustomerID", AttributeValue.builder().s("C123").build());

        ScanResponse scanResponse = ScanResponse.builder().items(List.of(item)).build();
        when(mockClient.scan(any(ScanRequest.class))).thenReturn(scanResponse);

        String customerId = CustomerService.login(mockClient, "john@gmail.com", "Password@1");
        assertEquals("C123", customerId);
    }

    @Test
    void testLoginWrongEmail() {
        ScanResponse scanResponse = ScanResponse.builder().items(List.of()).build();
        when(mockClient.scan(any(ScanRequest.class))).thenReturn(scanResponse);

        String customerId = CustomerService.login(mockClient, "wrong@gmail.com", "Password@1");
        assertNull(customerId);
    }

    @Test
    void testLoginWrongPassword() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Email", AttributeValue.builder().s("john@gmail.com").build());
        item.put("Password", AttributeValue.builder().s("Password@1").build());
        item.put("CustomerID", AttributeValue.builder().s("C123").build());

        ScanResponse scanResponse = ScanResponse.builder().items(List.of(item)).build();
        when(mockClient.scan(any(ScanRequest.class))).thenReturn(scanResponse);

        String customerId = CustomerService.login(mockClient, "john@gmail.com", "WrongPass@1");
        assertNull(customerId);
    }

    @Test
    void testGetCustomerNameSuccess() {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Name", AttributeValue.builder().s("John Doe").build());

        GetItemResponse response = GetItemResponse.builder().item(item).build();
        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        String name = CustomerService.getCustomerName(mockClient, "C123");
        assertEquals("John Doe", name);
    }

    @Test
    void testGetCustomerNameNotFound() {
        GetItemResponse response = GetItemResponse.builder().build();
        when(mockClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        String name = CustomerService.getCustomerName(mockClient, "C999");
        assertNull(name);
    }

    @Test
    void testUpdatePassword() {
        UpdateItemResponse mockResponse = UpdateItemResponse.builder().build();
        when(mockClient.updateItem(any(UpdateItemRequest.class))).thenReturn(mockResponse);

        CustomerService.updatePassword(mockClient, "C123", "NewPassword@1");

        ArgumentCaptor<UpdateItemRequest> captor = ArgumentCaptor.forClass(UpdateItemRequest.class);
        verify(mockClient).updateItem(captor.capture());
        assertEquals("C123", captor.getValue().key().get("CustomerID").s());
        assertEquals("NewPassword@1", captor.getValue().attributeUpdates().get("Password").value().s());
    }
}
