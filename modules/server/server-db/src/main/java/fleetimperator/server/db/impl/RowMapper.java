package fleetimperator.server.db.impl;

import fleetimperator.common.model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public interface RowMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}
