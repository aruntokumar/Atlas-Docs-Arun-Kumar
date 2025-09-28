package org.example;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@DynamoDbBean
public class AcceleratorDAXDemo04 {
    private String hashKey;
    private String range;

    @DynamoDbPartitionKey
    public String getHashKey() {
        return hashKey;
    }
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    @DynamoDbSortKey
    public String getRange() {
        return range;
    }
    public void setRange(String range) {
        this.range = range;
    }
}