package fleetimperator.server.app.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Responsible for loading the server configuration from various locations.
 */
public class ConfigLoader {
    private static final String PRODUCTION_LOCATION = "/opt/fleet-imperator/conf/system.properties";
    private static final String DEVELOPMENT_RESOURCE = "system.properties";

    /**
     * Load the server configuration, checking multiple places to find it.
     *
     * @return the {@link Properties} containing the server configuration
     */
    public static Properties load() {
        Properties properties = new Properties();
        try (InputStream inputStream = getConfigInputStream()) {
            properties.load(inputStream);
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to load system configuration file", ioException);
        }
        return properties;
    }

    private static InputStream getConfigInputStream() throws IOException {
        File productionConfigFile = new File(PRODUCTION_LOCATION);
        if (productionConfigFile.exists()) {
            return new FileInputStream(productionConfigFile);
        } else {
            URL developmentConfigFile = ConfigLoader.class.getClassLoader().getResource(DEVELOPMENT_RESOURCE);
            if (developmentConfigFile != null) {
                return developmentConfigFile.openStream();
            }
        }
        throw new RuntimeException("Unable to find system configuration file");
    }
}
