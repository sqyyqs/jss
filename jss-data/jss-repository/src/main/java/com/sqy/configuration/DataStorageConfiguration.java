package com.sqy.configuration;

import java.nio.file.Path;

public class DataStorageConfiguration {
    private DataStorageConfiguration() {
    }

    private static final String fileName = "storage.txt";

    public static Path getPath() {
        return Path.of(DataStorageConfiguration.class.getResource(("/" + fileName)).getPath());
    }

}
