package game.server.rest.resource.v1.game;

import game.common.model.Game;
import game.server.db.GameStore;

import javax.inject.Inject;
import javax.ws.rs.*;

import java.util.UUID;

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
        Game withId = game.setId(UUID.randomUUID().toString());
        gameStore.add(withId);
        return withId;
    }
}
