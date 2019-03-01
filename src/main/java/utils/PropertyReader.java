package utils;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static consts.PropertiesNames.BROWSER_NAME;
import static org.slf4j.LoggerFactory.getLogger;

class PropertyReader {
    private static final String GLOBAL_CONFIG = "config.properties";
    private static final Logger LOG = getLogger(PropertyReader.class);
    private static Map<String, String> globalProperties = new HashMap<>();

    static {
        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(Objects.requireNonNull(PropertyReader
                .class.getClassLoader().getResourceAsStream(GLOBAL_CONFIG)), Charset.forName("UTF-8")))) {
            LOG.info("LOAD GLOBAL CONFIG - " + GLOBAL_CONFIG);
            Properties newProp = new Properties();
            newProp.load(reader);
            for (String name : newProp.stringPropertyNames()) {
                globalProperties.put(name.contains("browser") ? "browserName" : name, newProp.getProperty(name));
            }
            System.out.println(globalProperties);
        } catch (IOException e) {
            LOG.error("Failed to load global properties: " + GLOBAL_CONFIG + "\n" + e);
            LOG.error(e.getMessage());
        }
    }

    static String getBrowserName() {
        return getGlobalProperty(BROWSER_NAME);
    }

    static String getGlobalProperty(String property) {
        return globalProperties.get(property);
    }
}
