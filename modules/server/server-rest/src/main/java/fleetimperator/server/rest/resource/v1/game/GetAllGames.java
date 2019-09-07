package fleetimperator.server.rest.resource.v1.game;

import fleetimperator.common.model.Game;
import fleetimperator.server.db.GameStore;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/games")
public class GetAllGames {
    private final GameStore gameStore;

    @Inject
    public GetAllGames(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<Game> getAllGames() {
        return gameStore.getAll();
    }
}
