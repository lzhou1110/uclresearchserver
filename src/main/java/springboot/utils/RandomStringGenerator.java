package springboot.utils;

import java.util.UUID;

public class RandomStringGenerator {
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
