package fleetimperator.server.app;

import fleetimperator.server.app.config.ConfigLoader;
import io.undertow.Undertow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class FleetImperator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FleetImperator.class);

    /**
     * Stands up a web server to process games.
     */
    private FleetImperator() {
        Properties properties = ConfigLoader.load();
        String bindHost = properties.getProperty("server.host");
        int bindPort = Integer.parseInt(properties.getProperty("server.port"));

        Undertow server = Undertow.builder()
                .addHttpListener(bindPort, bindHost)
                .build();
        server.start();
        LOGGER.info("{} server started {}:{}", FleetImperator.class.getSimpleName(), bindHost, bindPort);
    }

    /**
     * The entry-point into this application.
     *
     * @param args the command-line parameters
     */
    public static void main(String... args) {
        new FleetImperator();
    }
}
