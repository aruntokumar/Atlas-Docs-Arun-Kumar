package org.atlas.bank;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

public class CustomerService {

    private static final String CUSTOMER_TABLE = "Customer";

    // Create table if not exists
    public static void createTable(DynamoDbClient client) {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(CUSTOMER_TABLE).build());
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(CUSTOMER_TABLE)
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("CustomerID").attributeType("S").build())
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("CustomerID").keyType(KeyType.HASH).build())
                    .provisionedThroughput(ProvisionedThroughput.builder()
                            .readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build());
            System.out.println("✅ Customer table created.");
        }
    }

    // Customer registration
    public static void registerCustomer(DynamoDbClient client, Scanner sc) {
        String customerId = "C" + System.currentTimeMillis();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Contact: ");
        String contact = sc.nextLine();

        String email;
        while (true) {
            System.out.print("Enter Email (must contain @gmail.com): ");
            email = sc.nextLine();
            if (email != null && email.endsWith("@gmail.com")) break;
            else System.out.println("Invalid email format!");
        }

        String password;
        while (true) {
            System.out.print("Enter Password (min 8 chars, 1 uppercase, 1 lowercase, 1 special): ");
            password = sc.nextLine();
            if (BankingSystem.validatePassword(password)) break;
            else System.out.println("Password not valid!");
        }

        // Check for duplicates
        ScanResponse response = client.scan(ScanRequest.builder().tableName(CUSTOMER_TABLE).build());
        for (Map<String, AttributeValue> item : response.items()) {
            String existingEmail = item.get("Email") != null ? item.get("Email").s() : "";
            String existingContact = item.get("Contact") != null ? item.get("Contact").s() : "";
            String existingName = item.get("Name") != null ? item.get("Name").s() : "";
            String existingAddress = item.get("Address") != null ? item.get("Address").s() : "";
            String existingPassword = item.get("Password") != null ? item.get("Password").s() : "";

            if (existingEmail.equalsIgnoreCase(email) ||
                    existingContact.equals(contact) ||
                    (existingName.equalsIgnoreCase(name) &&
                            existingAddress.equalsIgnoreCase(address) &&
                            existingPassword.equals(password))) {
                System.out.println("❌ Customer already exists! Registration aborted.");
                return;
            }
        }

        // Insert new customer
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("CustomerID", AttributeValue.builder().s(customerId).build());
        item.put("Name", AttributeValue.builder().s(name).build());
        item.put("Address", AttributeValue.builder().s(address).build());
        item.put("Contact", AttributeValue.builder().s(contact).build());
        item.put("Email", AttributeValue.builder().s(email).build());
        item.put("Password", AttributeValue.builder().s(password).build());

        client.putItem(PutItemRequest.builder().tableName(CUSTOMER_TABLE).item(item).build());
        System.out.println("✅ Customer registration successful! CustomerID: " + customerId);

        // Create default account with transfer PIN
        AccountService.createAccount(client, sc, customerId);
    }

    // Customer login
    public static String login(DynamoDbClient client, String email, String password) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(CUSTOMER_TABLE).build());
        for (Map<String, AttributeValue> item : response.items()) {
            AttributeValue emailAttr = item.get("Email");
            AttributeValue passAttr = item.get("Password");
            if (emailAttr != null && passAttr != null) {
                if (email.equals(emailAttr.s()) && password.equals(passAttr.s())) {
                    return item.get("CustomerID").s();
                }
            }
        }
        return null;
    }

    // Get customer name
    public static String getCustomerName(DynamoDbClient client, String customerId) {
        GetItemResponse response = client.getItem(GetItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .build());
        if (response.hasItem() && response.item().get("Name") != null) {
            return response.item().get("Name").s();
        }
        return null;
    }

    // Get customer ID by email
    public static String getCustomerIdByEmail(DynamoDbClient client, String email) {
        ScanResponse response = client.scan(ScanRequest.builder().tableName(CUSTOMER_TABLE).build());
        for (Map<String, AttributeValue> item : response.items()) {
            AttributeValue emailAttr = item.get("Email");
            if (emailAttr != null && email.equals(emailAttr.s())) {
                return item.get("CustomerID").s();
            }
        }
        return null;
    }

    // Get customer contact
    public static String getCustomerContact(DynamoDbClient client, String customerId) {
        GetItemResponse response = client.getItem(GetItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .build());
        if (response.hasItem() && response.item().get("Contact") != null) {
            return response.item().get("Contact").s();
        }
        return null;
    }

    // Update customer password
    public static void updatePassword(DynamoDbClient client, String customerId, String newPassword) {
        client.updateItem(UpdateItemRequest.builder()
                .tableName(CUSTOMER_TABLE)
                .key(Map.of("CustomerID", AttributeValue.builder().s(customerId).build()))
                .attributeUpdates(Map.of("Password", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(newPassword).build())
                        .action(AttributeAction.PUT)
                        .build()))
                .build());
    }

    // Customer operations menu
    public static void customerOperations(DynamoDbClient client, Scanner sc, String customerId) {
        while (true) {
            System.out.println("\n--- Customer Operations ---");
            System.out.println("1-Deposit  2-Withdraw  3-Transfer  4-Transaction History  5-Logout");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    AccountService.deposit(client, sc, customerId);
                    break;
                case "2":
                    AccountService.withdraw(client, sc, customerId);
                    break;
                case "3":
                    AccountService.transfer(client, sc, customerId);
                    break;
                case "4":
                    AccountService.showTransactionHistory(client, sc, customerId);
                    break;
                case "5":
                    System.out.println("Customer logged out.");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
