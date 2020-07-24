package game.server.db;

import game.common.model.Game;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public interface GameStore {
    Optional<Game> get(String id);

    List<Game> getAll();

    int add(Collection<Game> games);
    default int add(Game... games) {
        return add(asList(games));
    }

    int update(Collection<Game> games);
    default int update(Game... games) {
        return update(asList(games));
    }

    int delete(Collection<String> ids);
    default int delete(String... ids) {
        return delete(asList(ids));
    }

    int truncate();
}
