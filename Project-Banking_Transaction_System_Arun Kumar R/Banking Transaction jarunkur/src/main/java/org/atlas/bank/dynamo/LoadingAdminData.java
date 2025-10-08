package org.atlas.bank.dynamo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoadingAdminData {

    //Supporting Class

    private static final String TABLE_NAME = "Admin";

//    public static void main(String[] args) throws Exception {
//        // Fake AWS credentials for local DynamoDB
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("fakeaccess", "fakeaccess");
//
//        // DynamoDB client for local
//        DynamoDbClient client = DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8001"))
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//
//        System.out.println("✅ Connection created successfully");
//
//        // 1️⃣ Ensure table exists
//        createTableIfNotExists(client);
//
//        // 2️⃣ Load JSON file
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream stream = LoadingAdminData.class.getClassLoader().getResourceAsStream("Admin.json");
//
//        if (stream == null) {
//            throw new RuntimeException("❌ Admin.json file not found in resources folder");
//        }
//
//        JsonNode rootNode = mapper.readTree(stream);
//        Iterator<JsonNode> iterator = rootNode.elements();
//
//        // 3️⃣ Insert data into table
//        while (iterator.hasNext()) {
//            JsonNode adminNode = iterator.next();
//            Map<String, AttributeValue> item = new HashMap<>();
//
//            item.put("AdminID", AttributeValue.builder().s(getText(adminNode, "AdminID")).build());
//            item.put("Name", AttributeValue.builder().s(getText(adminNode, "Name")).build());
//            item.put("Email", AttributeValue.builder().s(getText(adminNode, "Email")).build());
//            item.put("Password", AttributeValue.builder().s(getText(adminNode, "Password")).build());
//            item.put("Branch", AttributeValue.builder().s(getText(adminNode, "Branch")).build());
//
//            PutItemRequest request = PutItemRequest.builder()
//                    .tableName(TABLE_NAME)
//                    .item(item)
//                    .build();
//
//            client.putItem(request);
//            System.out.println("✅ Inserted Admin: " + getText(adminNode, "Name"));
//        }
//
//        client.close();
//       // System.out.println("✅ Closed DynamoDB client connection");
//    }

    // Helper: Create table if it does not exist
    private static void createTableIfNotExists(DynamoDbClient client) {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(TABLE_NAME).build());
            System.out.println("⚠️ Table '" + TABLE_NAME + "' already exists.");
        } catch (ResourceNotFoundException e) {
            System.out.println("ℹ️ Table '" + TABLE_NAME + "' not found. Creating...");

            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(TABLE_NAME)
                    .keySchema(
                            KeySchemaElement.builder().attributeName("AdminID").keyType(KeyType.HASH).build()
                    )
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("AdminID").attributeType(ScalarAttributeType.S).build()
                    )
                    .provisionedThroughput(
                            ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build()
                    )
                    .build();

            client.createTable(request);

            // Wait until table is active
            client.waiter().waitUntilTableExists(
                    DescribeTableRequest.builder().tableName(TABLE_NAME).build()
            );
            System.out.println("✅ Table '" + TABLE_NAME + "' created successfully!");
        }
    }

    // Helper: Safe JSON getter
    private static String getText(JsonNode node, String key) {
        return node.has(key) && !node.get(key).isNull() ? node.get(key).asText() : "N/A";
    }
}
