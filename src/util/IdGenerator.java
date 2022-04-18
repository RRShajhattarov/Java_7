package util;

public class IdGenerator {
    private static int num = 10;

    public static int generateId() {
        return ++num;
    }
}

