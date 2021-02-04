package net.pistonmaster.pistonclient.cache;

public class ServerMaxCache extends IntegerCache {
    private static final ServerMaxCache instance = new ServerMaxCache();

    public static ServerMaxCache get() {
        return instance;
    }
}
