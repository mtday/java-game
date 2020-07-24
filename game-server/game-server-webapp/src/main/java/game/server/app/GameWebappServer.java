package game.server.app;

import game.server.config.ConfigLoader;
import game.server.rest.Application;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.Properties;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class GameWebappServer {
    private GameWebappServer() {
        Properties properties = ConfigLoader.load();
        String contextPath = properties.getProperty("server.context.path");
        String apiPath = properties.getProperty("server.api.path");
        int port = Integer.parseInt(properties.getProperty("server.port"));

        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
        handler.setContextPath(contextPath);
        server.setHandler(handler);

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, apiPath);
        servletHolder.setInitOrder(1);
        servletHolder.setInitParameter("javax.ws.rs.Application", Application.class.getName());

        try {
            server.start();
            server.join();
        } catch (Exception failed) {
            throw new RuntimeException("Failed to start server", failed);
        } finally {
            server.destroy();
        }
    }

    public static void main(String... args) {
        new GameWebappServer();
    }
}
