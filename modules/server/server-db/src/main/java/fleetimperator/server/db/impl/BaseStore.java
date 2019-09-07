package fleetimperator.server.db.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BaseStore<T> {
    private final DataSource dataSource;

    BaseStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    private void populatePreparedStatement(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Collection) {
                // only collections containing strings are supported
                Object[] param = ((Collection) params[i]).toArray();
                ps.setArray(i + 1, ps.getConnection().createArrayOf("varchar", param));
            } else {
                ps.setObject(i + 1, params[i]);
            }
        }
    }

    Optional<T> getOne(RowMapper<T> rowMapper, String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            populatePreparedStatement(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return of(rowMapper.map(rs));
                } else {
                    return empty();
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to fetch object from database", sqlException);
        }
    }

    List<T> getList(RowMapper<T> rowMapper, String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            populatePreparedStatement(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(rowMapper.map(rs));
                }
                return results;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to fetch objects from database", sqlException);
        }
    }

    void update(RowSetter<T> rowSetter, String sql, Collection<T> objects) {
        if (objects.isEmpty()) {
            return;
        }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (T object : objects) {
                rowSetter.set(ps, object);
                ps.executeUpdate();
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to perform database update", sqlException);
        }
    }

    void update(String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            populatePreparedStatement(ps, params);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to perform database update", sqlException);
        }
    }
}
