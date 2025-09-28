package org.example;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.*;

@Service
public class AcceleratorDAXDemo04Service implements AcceleratorDAXDemo04Interface {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<AcceleratorDAXDemo04> table;

    public AcceleratorDAXDemo04Service(DynamoDbEnhancedClient client) {
        this.enhancedClient = client;
        this.table = enhancedClient.table("DaxDynamoTable", TableSchema.fromBean(AcceleratorDAXDemo04.class));
    }

    @Override
    public AcceleratorDAXDemo04 save(AcceleratorDAXDemo04 obj) {
        table.putItem(obj);
        return obj;
    }

    @Override
    public AcceleratorDAXDemo04 load(String hashKey, String rangeKey) {
        return table.getItem(r -> r.key(k -> k.partitionValue(hashKey).sortValue(rangeKey)));
    }
}