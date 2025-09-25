package org.example.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class WorkloggerProperties {
    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        try {
            PROPS.load(new FileReader("files/worklogger.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties-file", e);
        }
    }
}
