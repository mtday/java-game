package game.server.rest.resource.v1.game;

import game.common.model.Game;
import game.server.db.GameStore;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/games/{id}")
public class GetGame {
    private final GameStore gameStore;

    @Inject
    public GetGame(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Game getGame(@PathParam("id") String id) {
        return gameStore.get(id)
                .orElseThrow(() -> new NotFoundException("Game with id " + id + " not found"));
    }
}
