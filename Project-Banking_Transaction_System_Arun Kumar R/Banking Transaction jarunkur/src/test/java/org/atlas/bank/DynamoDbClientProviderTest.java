package org.atlas.bank;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDbClientProviderTest {

    @Test
    void testSingletonClient() {
        DynamoDbClient client1 = DynamoDbClientProvider.getClient();
        DynamoDbClient client2 = DynamoDbClientProvider.getClient();

        assertNotNull(client1);
        assertSame(client1, client2, "Client should be a singleton");
    }
}
