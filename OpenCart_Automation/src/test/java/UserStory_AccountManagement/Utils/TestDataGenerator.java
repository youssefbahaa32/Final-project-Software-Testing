package UserStory_AccountManagement.Utils;

import java.util.Random;

public class TestDataGenerator {

    private static final Random random = new Random();

    public static String generateRandomEmail() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return "testuser" + timestamp + "@test.com";
    }

    public static String generateRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emma"};
        return firstNames[random.nextInt(firstNames.length)];
    }

    public static String generateRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia"};
        return lastNames[random.nextInt(lastNames.length)];
    }

    public static String generateRandomPhone() {
        return String.format("%010d", random.nextInt(1000000000));
    }

    public static String generateRandomPassword() {
        return "Test" + random.nextInt(10000) + "Pass!";
    }

    public static String generateTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
