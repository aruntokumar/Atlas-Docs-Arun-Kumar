package org.atlas.bank;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbClientProvider {

    // Single shared DynamoDbClient instance (Singleton pattern)
    private static DynamoDbClient client;

    private DynamoDbClientProvider() {
        // private constructor to prevent instantiation
    }

    public static DynamoDbClient getClient() {
        if (client == null) {
            client = DynamoDbClient.builder()
                    .region(Region.AP_SOUTH_1)  // change to your AWS region
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        }
        return client;
    }
}
