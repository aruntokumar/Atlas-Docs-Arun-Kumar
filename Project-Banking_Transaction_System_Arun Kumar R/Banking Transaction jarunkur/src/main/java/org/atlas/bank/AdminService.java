package org.atlas.bank;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminService {

    private static final String ADMIN_TABLE = "Admin";
    private static final String CUSTOMER_TABLE = "Customer";
    private static final String DELETE_CUSTOMER_TABLE = "DeleteCustomer";

    // Check if login email/password belongs to Admin
    public static boolean isAdmin(DynamoDbClient client, String email, String password) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(ADMIN_TABLE).build());

        for (Map<String, AttributeValue> item : response.items()) {
            String itemEmail = item.get("Email") != null ? item.get("Email").s() : "";
            String itemPassword = item.get("Password") != null ? item.get("Password").s() : "";

            if (itemEmail.equals(email) && itemPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Verify contact number for admin password reset
    public static boolean verifyContact(DynamoDbClient client, String email, String contact) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(ADMIN_TABLE).build());
        for (Map<String, AttributeValue> item : response.items()) {
            String itemEmail = item.get("Email") != null ? item.get("Email").s() : "";
            String itemContact = item.get("Contact") != null ? item.get("Contact").s() : "";

            if (itemEmail.equals(email) && itemContact.equals(contact)) {
                return true;
            }
        }
        return false;
    }

    // Update admin password
    public static void updatePassword(DynamoDbClient client, String email, String newPassword) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(ADMIN_TABLE).build());
        for (Map<String, AttributeValue> item : response.items()) {
            String itemEmail = item.get("Email") != null ? item.get("Email").s() : "";
            if (itemEmail.equals(email)) {
                String adminId = item.get("AdminID").s();
                client.updateItem(UpdateItemRequest.builder()
                        .tableName(ADMIN_TABLE)
                        .key(Map.of("AdminID", AttributeValue.builder().s(adminId).build()))
                        .attributeUpdates(Map.of("Password",
                                AttributeValueUpdate.builder()
                                        .value(AttributeValue.builder().s(newPassword).build())
                                        .action(AttributeAction.PUT)
                                        .build()))
                        .build());
                break;
            }
        }
    }

    // Admin operations menu
    public static void adminOperations(DynamoDbClient client, Scanner sc) {
        while (true) {
            System.out.println("\n=== Admin Operations ===");
            System.out.println("1-View All Customers");
            System.out.println("2-Add Customer");
            System.out.println("3-Delete Customer");
            System.out.println("4-Deposit");
            System.out.println("5-Withdraw");
            System.out.println("6-Transfer");
            System.out.println("7-Transaction History");
            System.out.println("8-Update Customer Details");
            System.out.println("9-Logout");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewAllCustomers(client);
                    break;
                case "2":
                    CustomerService.registerCustomer(client, sc); // Reuse customer registration
                    break;
                case "3":
                    deleteCustomer(client, sc);
                    break;
                case "4":
                    depositToCustomer(client, sc);
                    break;
                case "5":
                    withdrawFromCustomer(client, sc);
                    break;
                case "6":
                    transferForCustomer(client, sc);
                    break;
                case "7":
                    viewCustomerTransactionHistory(client, sc);
                    break;
                case "8":
                    updateCustomerDetails(client, sc);
                    break;
                case "9":
                    System.out.println("Admin logged out.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------- Helper Methods ----------------

    private static void viewAllCustomers(DynamoDbClient client) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(CUSTOMER_TABLE).build());
        List<Map<String, AttributeValue>> items = response.items();

        System.out.println("\n--- Customer List ---");
        for (Map<String, AttributeValue> item : items) {
            System.out.println("CustomerID: " + item.get("CustomerID").s() +
                    " | Name: " + item.get("Name").s() +
                    " | Email: " + item.get("Email").s());
        }
    }

    private static void deleteCustomer(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter CustomerID to delete: ");
        String customerId = sc.nextLine();

        GetItemResponse getResponse = client.getItem(GetItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .build());

        if (!getResponse.hasItem()) {
            System.out.println("CustomerID not found!");
            return;
        }

        Map<String, AttributeValue> customerItem = getResponse.item();

        // Move to DeleteCustomer table
        client.putItem(PutItemRequest.builder()
                .tableName(DELETE_CUSTOMER_TABLE)
                .item(customerItem)
                .build());

        // Delete from Customer table
        client.deleteItem(DeleteItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .build());

        System.out.println("Customer deleted and moved to DeleteCustomer table.");
    }

    private static void depositToCustomer(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter CustomerID for deposit: ");
        String customerId = sc.nextLine();
        AccountService.deposit(client, sc, customerId);
    }

    private static void withdrawFromCustomer(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter CustomerID for withdrawal: ");
        String customerId = sc.nextLine();
        AccountService.withdraw(client, sc, customerId);
    }

    private static void transferForCustomer(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter Source CustomerID: ");
        String customerId = sc.nextLine();
        AccountService.transfer(client, sc, customerId);
    }

    private static void viewCustomerTransactionHistory(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter CustomerID to view transaction history: ");
        String customerId = sc.nextLine();

        System.out.println("\n--- Accounts of CustomerID: " + customerId + " ---");
        // Scan accounts to get all accounts of this customer
        ScanResponse accountResponse = client.scan(ScanRequest.builder().tableName("Account").build());
        for (Map<String, AttributeValue> account : accountResponse.items()) {
            if (account.get("CustomerID").s().equals(customerId)) {
                String accountId = account.get("AccountID").s();
                System.out.println("\nAccountID: " + accountId);
                AccountService.showTransactionHistory(client, new Scanner(System.in), customerId);
            }
        }
    }

    // Optional: create DeleteCustomer table if not exists
    public static void createDeleteCustomerTable(DynamoDbClient client) {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(DELETE_CUSTOMER_TABLE).build());
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(DELETE_CUSTOMER_TABLE)
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("CustomerID").attributeType("S").build())
                    .keySchema(KeySchemaElement.builder().attributeName("CustomerID").keyType(KeyType.HASH).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build());
        }
    }

    // ✅ Update Customer Details (Email / Contact / Address)
    private static void updateCustomerDetails(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter CustomerID to update: ");
        String customerId = sc.nextLine();

        // Check if customer exists
        GetItemResponse getResponse = client.getItem(GetItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .build());

        if (!getResponse.hasItem()) {
            System.out.println("❌ CustomerID not found!");
            return;
        }

        System.out.println("Which detail you want to update?");
        System.out.println("1-Email  2-Contact  3-Name  4-Address");
        String choice = sc.nextLine();

        String fieldName = null;
        String newValue = null;

        switch (choice) {
            case "1":
                fieldName = "Email";
                System.out.print("Enter new Email (must end with @gmail.com): ");
                newValue = sc.nextLine();
                if (!newValue.endsWith("@gmail.com")) {
                    System.out.println("❌ Invalid Email format!");
                    return;
                }
                break;
            case "2":
                fieldName = "Contact";
                System.out.print("Enter new Contact: ");
                newValue = sc.nextLine();
                break;
            case "3":
                fieldName = "Name";
                System.out.print("Enter new Name: ");
                newValue = sc.nextLine();
                break;
            case "4":
                fieldName = "Address";
                System.out.print("Enter new Address: ");
                newValue = sc.nextLine();
                break;
            default:
                System.out.println("❌ Invalid option!");
                return;
        }

        // Update in Customer table
        client.updateItem(UpdateItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .attributeUpdates(Map.of(fieldName, AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(newValue).build())
                        .action(AttributeAction.PUT)
                        .build()))
                .build());

//        // Update in Account table for all accounts of this customer
//        ScanResponse accountResponse = client.scan(ScanRequest.builder().tableName("Account").build());
//        for (Map<String, AttributeValue> account : accountResponse.items()) {
//            if (account.get("CustomerID").s().equals(customerId)) {
//                String accountId = account.get("AccountID").s();
//                client.updateItem(UpdateItemRequest.builder()
//                        .tableName("Account")
//                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
//                        .attributeUpdates(Map.of(fieldName, AttributeValueUpdate.builder()
//                                .value(AttributeValue.builder().s(newValue).build())
//                                .action(AttributeAction.PUT)
//                                .build()))
//                        .build());
//            }
//        }

        // Update in Admin table if exists
        try {
            ScanResponse adminResponse = client.scan(ScanRequest.builder().tableName(ADMIN_TABLE).build());
            for (Map<String, AttributeValue> admin : adminResponse.items()) {
                if (admin.get("CustomerID") != null && admin.get("CustomerID").s().equals(customerId)) {
                    String adminId = admin.get("AdminID").s();
                    client.updateItem(UpdateItemRequest.builder()
                            .tableName(ADMIN_TABLE)
                            .key(Map.of("AdminID", AttributeValue.builder().s(adminId).build()))
                            .attributeUpdates(Map.of(fieldName, AttributeValueUpdate.builder()
                                    .value(AttributeValue.builder().s(newValue).build())
                                    .action(AttributeAction.PUT)
                                    .build()))
                            .build());
                }
            }
        } catch (Exception ignored) {}

        System.out.println("✅ " + fieldName + " updated successfully for CustomerID: " + customerId);
    }
}
