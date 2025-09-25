package org.example.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WorkloggerProperties {
    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        try (InputStream input = WorkloggerProperties.class.getClassLoader()
                .getResourceAsStream("worklogger.properties")) {
            if (input == null) throw new RuntimeException("Properties file not found in resources");
            PROPS.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties-file", e);
        }
    }
}