package fleetimperator.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("test")
public class TestResource {
    @GET
    @Produces(APPLICATION_JSON)
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        return map;
    }
}
