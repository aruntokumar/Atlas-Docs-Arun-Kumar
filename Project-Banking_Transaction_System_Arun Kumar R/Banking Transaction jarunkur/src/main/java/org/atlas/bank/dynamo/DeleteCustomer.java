package org.atlas.bank.dynamo;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.*;

public class DeleteCustomer {


    //Supporting Class

    // Ensure DeletedCustomers table exists
    private static void createDeletedCustomersTableIfNotExists(DynamoDbClient client) {
        String tableName = "DeletedCustomers";
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(tableName).build());
            System.out.println("✅ Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("⚡ Creating table: " + tableName);
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(tableName)
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("CustomerID")
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("CustomerID")
                            .keyType(KeyType.HASH)
                            .build())
                    .provisionedThroughput(ProvisionedThroughput.builder()
                            .readCapacityUnits(5L)
                            .writeCapacityUnits(5L)
                            .build())
                    .build();
            client.createTable(request);
        }
    }

//    public static void main(String[] args) {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("fakeaccess", "fakeaccess");
//
//        DynamoDbClient client = DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8001"))
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//
//        System.out.println("✅ Connected to DynamoDB");
//
//        // Ensure DeletedCustomers table exists
//        createDeletedCustomersTableIfNotExists(client);
//
//        // Simulate logged-in user
//        String loggedInUserEmail = "admin1@bank.com";  // Example
//        boolean isAdmin = checkIfAdmin(loggedInUserEmail);
//
//        if (!isAdmin) {
//            System.out.println("❌ Access denied! Only Admins can delete customers.");
//            client.close();
//            return;
//        }
//
//        String customerIdToDelete = "C1758007722172"; // Example
//
//        try {
//            // 1. Get customer details
//            GetItemRequest getCustomerReq = GetItemRequest.builder()
//                    .tableName("Customer")
//                    .key(Map.of("CustomerID", AttributeValue.builder().s(customerIdToDelete).build()))
//                    .build();
//
//            Map<String, AttributeValue> customerItem = client.getItem(getCustomerReq).item();
//
//            if (customerItem == null || customerItem.isEmpty()) {
//                System.out.println("⚠️ Customer not found: " + customerIdToDelete);
//            } else {
//                System.out.println("✅ Customer found: " + customerItem);
//
//                // 2. Scan Account table to get all accounts for this customer
//                ScanRequest scanRequest = ScanRequest.builder()
//                        .tableName("Account")
//                        .filterExpression("CustomerID = :cid")
//                        .expressionAttributeValues(Map.of(":cid", AttributeValue.builder().s(customerIdToDelete).build()))
//                        .build();
//
//                List<Map<String, AttributeValue>> accounts = client.scan(scanRequest).items();
//
//                // 3. Prepare combined record for DeletedCustomers
//                Map<String, AttributeValue> deletedRecord = new HashMap<>(customerItem);
//
//                if (!accounts.isEmpty()) {
//                    List<AttributeValue> accountList = new ArrayList<>();
//                    for (Map<String, AttributeValue> acc : accounts) {
//                        accountList.add(AttributeValue.builder().m(acc).build());
//                    }
//                    deletedRecord.put("DeletedAccounts", AttributeValue.builder().l(accountList).build());
//                }
//
//                // 4. Insert into DeletedCustomers
//                PutItemRequest putReq = PutItemRequest.builder()
//                        .tableName("DeletedCustomers")
//                        .item(deletedRecord)
//                        .build();
//                client.putItem(putReq);
//                System.out.println("🗑️ Customer + accounts archived into DeletedCustomers table");
//
//                // 5. Delete customer from Customer table
//                DeleteItemRequest delCustomerReq = DeleteItemRequest.builder()
//                        .tableName("Customer")
//                        .key(Map.of("CustomerID", AttributeValue.builder().s(customerIdToDelete).build()))
//                        .build();
//                client.deleteItem(delCustomerReq);
//                System.out.println("✅ Customer deleted from Customer table");
//
//                // 6. Delete accounts from Account table
//                for (Map<String, AttributeValue> acc : accounts) {
//                    String accId = acc.get("AccountID").s();
//                    DeleteItemRequest delAccReq = DeleteItemRequest.builder()
//                            .tableName("Account")
//                            .key(Map.of("AccountID", AttributeValue.builder().s(accId).build()))
//                            .build();
//                    client.deleteItem(delAccReq);
//                    System.out.println("✅ Deleted account: " + accId);
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ Error while deleting customer: " + e.getMessage());
//        } finally {
//            client.close();
//            System.out.println("🔒 Connection closed");
//        }
//    }

    // Dummy admin check
    private static boolean checkIfAdmin(String email) {
        // In reality, query the Admin table
        return email.endsWith("@bank.com");
    }
}
