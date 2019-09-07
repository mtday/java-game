package fleetimperator.server.db;

import fleetimperator.common.model.Game;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public interface GameStore {
    Optional<Game> get(String id);

    List<Game> getAll();

    void add(Collection<Game> games);
    default void add(Game... games) {
        add(asList(games));
    }

    void update(Collection<Game> games);
    default void update(Game... games) {
        update(asList(games));
    }

    void delete(Collection<String> ids);
    default void delete(String... ids) {
        delete(asList(ids));
    }
}
