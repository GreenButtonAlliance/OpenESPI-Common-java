package org.energyos.espi.common.test;

import java.util.UUID;

public class CucumberSession {
    private static String username;
    private static UUID uuid;
    private static String userHashedId;
    private static String usagePointHashedId;
    private static int numberOfSubscriptions;

    private CucumberSession() {}

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CucumberSession.username = username;
    }

    public static UUID getUUID() {
        return uuid;
    }

    public static void setUUID(UUID UUID) {
        CucumberSession.uuid = UUID;
    }

    public static String getUserHashedId() {
        return userHashedId;
    }

    public static void setUserHashedId(String userHashedId) {
        CucumberSession.userHashedId = userHashedId;
    }

    public static void setUsagePointHashedId(String usagePointHashedId) {
        CucumberSession.usagePointHashedId = usagePointHashedId;
    }

    public static String getUsagePointHashedId() {
        return usagePointHashedId;
    }

    public static void setNumberOfSubscriptions(int numberOfSubscriptions) {
        CucumberSession.numberOfSubscriptions = numberOfSubscriptions;
    }

    public static int getNumberOfSubscriptions() {
        return numberOfSubscriptions;
    }
}
