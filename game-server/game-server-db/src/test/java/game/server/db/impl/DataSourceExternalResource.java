package game.server.db.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.util.function.Supplier;

public class DataSourceExternalResource extends ExternalResource implements Supplier<DataSource> {
    private DataSource dataSource;

    @Override
    public DataSource get() {
        return dataSource;
    }

    @Override
    public void before() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:game");
        config.setDriverClassName("org.h2.Driver");
        config.setUsername("SA");
        config.setPassword("");
        config.setAutoCommit(true);

        dataSource = new HikariDataSource(config);
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
