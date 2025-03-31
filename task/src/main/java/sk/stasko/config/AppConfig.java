package sk.stasko.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final static String APP_CONFIG_FILE = "app.properties";

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(APP_CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException(APP_CONFIG_FILE + " not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
