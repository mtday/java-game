package game.server.rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends ResourceConfig {
    public Application() {
        packages(true, Application.class.getPackageName());

        register(new DependencyInjectionBinder());
    }
}
