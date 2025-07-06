package day6_java;

import java.util.Arrays;

public class HashTable<Key, Value> {

    private class HashTableNode {
        Key key;
        Value value;
        boolean active;
        boolean tombstoned;

        HashTableNode() {
            key = null;
            value = null;
            active = false;
            tombstoned = false;
        }

        HashTableNode(Key k, Value v) {
            key = k;
            value = v;
            active = true;
            tombstoned = false;
        }
    }

    private static final int TABLE_SIZE = 9;
    private Object[] table;   // Can't use generic array: Java doesn't allow it :contentReference[oaicite:1]{index=1}
    private int size = 0;
    private int tombstones = 0;

    public HashTable() {
        table = new Object[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new HashTableNode();
        }
    }

    @SuppressWarnings("unchecked")
    private HashTableNode nodeAt(int index) {
        return (HashTableNode) table[index];
    }

    public Value put(Key key, Value value) {
        int idx = Math.abs(key.hashCode()) % TABLE_SIZE;
        int firstTomb = -1;

        for (int i = 0; i < TABLE_SIZE; i++) {
            int j = (idx + i) % TABLE_SIZE;
            HashTableNode node = nodeAt(j);

            if (!node.active) {
                if (!node.tombstoned) {
                    if (firstTomb != -1) j = firstTomb;
                    table[j] = new HashTableNode(key, value);
                    size++;
                    return null;
                } else if (firstTomb == -1) {
                    firstTomb = j;
                }
            } else if (node.key.equals(key)) {
                Value old = node.value;
                node.value = value;
                return old;
            }
        }

        if (firstTomb != -1) {
            table[firstTomb] = new HashTableNode(key, value);
            size++;
            tombstones--;
            return null;
        }

        throw new IllegalStateException("HashTable is full");
    }

    public Value get(Key key) {
        int idx = Math.abs(key.hashCode()) % TABLE_SIZE;
        for (int i = 0; i < TABLE_SIZE; i++) {
            int j = (idx + i) % TABLE_SIZE;
            HashTableNode node = nodeAt(j);
            if (!node.active && !node.tombstoned) return null;
            if (node.active && node.key.equals(key)) return node.value;
        }
        return null;
    }

    public Value remove(Key key) {
        int idx = Math.abs(key.hashCode()) % TABLE_SIZE;
        for (int i = 0; i < TABLE_SIZE; i++) {
            int j = (idx + i) % TABLE_SIZE;
            HashTableNode node = nodeAt(j);
            if (!node.active && !node.tombstoned) return null;
            if (node.active && node.key.equals(key)) {
                Value old = node.value;
                node.active = false;
                node.tombstoned = true;
                node.key = null;
                node.value = null;
                size--;
                tombstones++;
                return old;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}