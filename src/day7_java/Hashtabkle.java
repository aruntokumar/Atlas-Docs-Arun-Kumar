package day07_java;

public class HashTable<Key, Value> {
    private class HashTableNode {
        private Key key;
        private Value value;
        private boolean active;
        private boolean tombstoned;

        public HashTableNode() {
            key = null;
            value = null;
            active = false;
            tombstoned = false;
        }

        public HashTableNode(Key initKey, Value initData) {
            key = initKey;
            value = initData;
            active = true;
            tombstoned = false;
        }
    }

    private final static int TABLE_SIZE = 9;
    private Object[] table;

    public HashTable() {
        table = new Object[TABLE_SIZE];
        for (int j = 0; j < TABLE_SIZE; j++) {
            table[j] = new HashTableNode();
        }
    }

    private int hash(Key key) {
        return Math.abs(key.hashCode()) % TABLE_SIZE;
    }

    @SuppressWarnings("unchecked")
    public Value put(Key key, Value value) {
        int index = hash(key);
        int originalIndex = index;
        int tombstoneIndex = -1;

        do {
            HashTableNode node = (HashTableNode) table[index];

            if (!node.active && !node.tombstoned) {
                // Found an empty slot
                if (tombstoneIndex != -1) index = tombstoneIndex;
                table[index] = new HashTableNode(key, value);
                return null;
            }

            if (!node.active && node.tombstoned && tombstoneIndex == -1) {
                // Record tombstone index
                tombstoneIndex = index;
            }

            if (node.active && node.key.equals(key)) {
                // Key already exists, replace value
                Value oldValue = node.value;
                node.value = value;
                return oldValue;
            }

            index = (index + 1) % TABLE_SIZE;
        } while (index != originalIndex);

        // Table is full
        return null;
    }

    @SuppressWarnings("unchecked")
    public Value get(Key key) {
        int index = hash(key);
        int originalIndex = index;

        do {
            HashTableNode node = (HashTableNode) table[index];

            if (!node.active && !node.tombstoned) {
                return null; // Key not found
            }

            if (node.active && node.key.equals(key)) {
                return node.value;
            }

            index = (index + 1) % TABLE_SIZE;
        } while (index != originalIndex);

        return null;
    }

    @SuppressWarnings("unchecked")
    public Value remove(Key key) {
        int index = hash(key);
        int originalIndex = index;

        do {
            HashTableNode node = (HashTableNode) table[index];

            if (!node.active && !node.tombstoned) {
                return null; // Key not found
            }

            if (node.active && node.key.equals(key)) {
                Value oldValue = node.value;
                node.active = false;
                node.tombstoned = true;
                node.key = null;
                node.value = null;
                return oldValue;
            }

            index = (index + 1) % TABLE_SIZE;
        } while (index != originalIndex);

        return null;
    }
}