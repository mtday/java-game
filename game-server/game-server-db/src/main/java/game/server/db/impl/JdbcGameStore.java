package game.server.db.impl;

import game.common.model.Game;
import game.server.db.GameStore;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JdbcGameStore extends BaseStore<Game> implements GameStore {
    public JdbcGameStore(DataSource dataSource) {
        super(dataSource);
    }

    private static final RowMapper<Game> ROW_MAPPER = rs ->
            new Game()
                    .setId(rs.getString("id"))
                    .setTurn(rs.getInt("turn"));

    private static final RowSetter<Game> INSERT_ROW_SETTER = (ps, game) -> {
        int index = 0;
        ps.setString(++index, game.getId());
        ps.setInt(++index, game.getTurn());
    };

    private static final RowSetter<Game> UPDATE_ROW_SETTER = (ps, game) -> {
        int index = 0;
        ps.setInt(++index, game.getTurn());
        ps.setString(++index, game.getId());
    };

    @Override
    public Optional<Game> get(String id) {
        return getOne(ROW_MAPPER, "SELECT * FROM games WHERE id = ?", id);
    }

    @Override
    public List<Game> getAll() {
        return getList(ROW_MAPPER, "SELECT * FROM games");
    }

    @Override
    public int add(Collection<Game> games) {
        return update(INSERT_ROW_SETTER, "INSERT INTO games (id, turn) VALUES (?, ?)", games);
    }

    @Override
    public int update(Collection<Game> games) {
        return update(UPDATE_ROW_SETTER, "UPDATE games SET turn = ? WHERE id = ?", games);
    }

    @Override
    public int delete(Collection<String> ids) {
        return update("DELETE FROM games WHERE id = ANY(?)", ids);
    }

    @Override
    public int truncate() {
        return update("DELETE FROM games");
    }
}
