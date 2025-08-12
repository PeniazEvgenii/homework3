package ru.aston.hometask.config;

public class PathConfiguration {
    private static final String ENV_APP_PATH = System.getenv("STORE_URL");
    private static final String DEFAULT_URL = "src/main/resources/";

    private final String storePath;

    public PathConfiguration() {
        this.storePath = initPath();
    }

    public String getStorePath() {
        return storePath;
    }

    private String initPath() {
        return ENV_APP_PATH != null ? ENV_APP_PATH : DEFAULT_URL;
    }
}
