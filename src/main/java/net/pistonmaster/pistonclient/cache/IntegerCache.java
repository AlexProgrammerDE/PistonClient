package net.pistonmaster.pistonclient.cache;

import java.util.HashMap;

public abstract class IntegerCache {
    private final HashMap<String, Integer> map = new HashMap<>();

    public void put(String str, int integer) {
        map.put(str, integer);
    }

    public int get(String str) {
        return map.get(str);
    }
}
