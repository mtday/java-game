package game.server.rest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import game.server.config.ConfigLoader;
import game.server.db.GameStore;
import game.server.db.impl.JdbcGameStore;
import org.flywaydb.core.Flyway;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.sql.DataSource;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class DependencyInjectionBinder extends AbstractBinder {
    @Override
    protected void configure() {
        Properties props = getProperties();
        bind(props).to(Properties.class);

        DataSource dataSource = getDataSource(props);
        bind(dataSource).to(DataSource.class);

        GameStore gameStore = new JdbcGameStore(dataSource);
        bind(gameStore).to(GameStore.class);
    }

    private Properties getProperties() {
        return ConfigLoader.load();
    }

    private DataSource getDataSource(Properties props) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty("db.url"));
        config.setDriverClassName(props.getProperty("db.driver"));
        config.setUsername(props.getProperty("db.user"));
        config.setPassword(props.getProperty("db.pass"));
        config.setMinimumIdle(parseInt(props.getProperty("db.min.idle")));
        config.setMaximumPoolSize(parseInt(props.getProperty("db.max.pool.size")));
        config.setIdleTimeout(parseInt(props.getProperty("db.idle.timeout")));
        config.setConnectionTimeout(parseInt(props.getProperty("db.connection.timeout")));
        config.setAutoCommit(true);

        HikariDataSource dataSource = new HikariDataSource(config);
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
        return dataSource;
    }
}
