package fleetimperator.server.rest.resource.v1.game;

import fleetimperator.common.model.Game;
import fleetimperator.server.db.GameStore;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/games")
public class UpdateGame {
    private final GameStore gameStore;

    @Inject
    public UpdateGame(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Game updateGame(Game game) {
        gameStore.update(game);
        return game;
    }
}
