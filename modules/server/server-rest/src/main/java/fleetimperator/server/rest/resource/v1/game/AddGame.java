package fleetimperator.server.rest.resource.v1.game;

import fleetimperator.common.model.Game;
import fleetimperator.server.db.GameStore;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/games")
public class AddGame {
    private final GameStore gameStore;

    @Inject
    public AddGame(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Game addGame(Game game) {
        Game withId = new Game.Builder(game).build(); // make sure it has an id
        gameStore.add(withId);
        return withId;
    }
}
