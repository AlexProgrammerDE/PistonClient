package net.pistonmaster.pistonclient.cache;

import java.util.HashMap;

public abstract class IntegerCache {
    private final HashMap<String, Integer> map = new HashMap<>();

    public void put(String str, int integer) {
        map.put(str, integer);
    }

    public Integer get(String str) {
        try {
            return map.get(str);
        } catch (Exception e) {
            return null;
        }
    }
}
