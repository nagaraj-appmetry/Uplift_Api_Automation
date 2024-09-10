package main.utilities.utilitiesapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    static {

        try (InputStream inStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inStream == null) {
                throw new FileNotFoundException("config.properties file not found in the classpath");
            }
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

