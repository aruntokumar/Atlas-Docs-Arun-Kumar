package org.atlas.bank.dynamo;

public class deletecol {
    //TO Delete data
//    public static void main(String[] args) {
//        DynamoDbClient client = DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8001")) // DynamoDB local
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(
//                                AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")
//                        )
//                )
//                .build();
//
//        String tableName = "Customer";
//
//        // Correct key type: String, not number
//        Map<String, AttributeValue> key = Map.of(
//                "CustomerID", AttributeValue.builder().s("C1757905123488").build()
//        );
//
//        // Update expression to remove attribute
//        UpdateItemRequest updateRequest = UpdateItemRequest.builder()
//                .tableName(tableName)
//                .key(key)
//                .updateExpression("REMOVE PhoneNo")
//                .build();
//
//        client.updateItem(updateRequest);
//
//        System.out.println("Attribute 'PhoneNo' deleted successfully!");
//        client.close();
//    }
}
