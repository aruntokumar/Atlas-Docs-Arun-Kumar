package day6_java;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class task006 {
    public static void main(String[] args) {
        // Create a standard HashMap
        Map<String, Integer> map = new HashMap<>();

        // Insert first null key
        map.put(null, 100);
        System.out.println("After first null key: " + map);

        // Insert second null key (overwrites previous)
        map.put(null, 200);
        System.out.println("After second null key: " + map);

        // Wrap with synchronizedMap for thread safety
        Map<String, Integer> syncMap = Collections.synchronizedMap(map);

        // Add more entries
        syncMap.put("one", 1);
        syncMap.put("two", 2);

        System.out.println("Contents of synchronized map:");
        synchronized (syncMap) {
            Iterator<Map.Entry<String, Integer>> it = syncMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> e = it.next();
                System.out.println("  " + e.getKey() + " → " + e.getValue());
            }
        }
    }
}