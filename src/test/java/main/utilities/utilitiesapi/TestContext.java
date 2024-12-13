package main.utilities.utilitiesapi;

import java.util.List;

public class TestContext {
    private static TestContext instance;
    private String authorizationToken;

    private TestContext() {}

    public static synchronized TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String token) {
        this.authorizationToken = token;
    }

    private static List<String> groupIds;
    public static List<String> getGroupIds() {
        return groupIds;
    }

    public static void setGroupIds(List<String> ids) {
        groupIds = ids;
    }
}
