package fleetimperator.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("another")
public class AnotherResource {
    @GET
    @Produces(APPLICATION_JSON)
    public String test() {
        return "Another";
    }
}
