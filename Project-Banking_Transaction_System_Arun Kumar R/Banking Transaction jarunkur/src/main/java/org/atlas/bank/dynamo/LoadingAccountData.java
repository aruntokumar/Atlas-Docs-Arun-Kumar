package org.atlas.bank.dynamo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoadingAccountData {

    //Supporting Class

//    public static void main(String[] args) throws Exception {
//
//        // 1. AWS Credentials
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("fakeaccess", "fakeaccess");
//
//        // 2. DynamoDB client
//        DynamoDbClient client = DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8001"))
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//
//        System.out.println("✅ Connected to DynamoDB");
//
//        String tableName = "Account";
//
//        // 3. Load JSON file from resources
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream stream = LoadingAccountData.class.getClassLoader()
//                .getResourceAsStream("Account.json");
//
//        if (stream == null) {
//            System.out.println("❌ Account.json file not found in resources!");
//            client.close();
//            return;
//        }
//
//        JsonNode rootNode = mapper.readTree(stream);
//        Iterator<JsonNode> iterator = rootNode.elements();
//
//        // 4. Loop through JSON and insert each account
//        while (iterator.hasNext()) {
//            JsonNode node = iterator.next();
//            Map<String, AttributeValue> item = new HashMap<>();
//
//            item.put("AccountID", AttributeValue.builder().s(node.get("AccountID").asText()).build());
//            item.put("CustomerID", AttributeValue.builder().s(node.get("CustomerID").asText()).build());
//            item.put("AccountType", AttributeValue.builder().s(node.get("AccountType").asText()).build());
//            item.put("Balance", AttributeValue.builder().n(node.get("Balance").asText()).build());
//
//
//            PutItemRequest request = PutItemRequest.builder()
//                    .tableName(tableName)
//                    .item(item)
//                    .build();
//
//            client.putItem(request);
//            // System.out.println("✅ Inserted AccountID: " + node.get("AccountID").asText());
//        }
//
//        client.close();
//        System.out.println("🔒 All data loaded and connection closed");
//    }
}
