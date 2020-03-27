package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.runtime.context.scope.Refreshable;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Default factory for creating Native ClickHouse client
 * {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.3.2020
 */
@Requires(beans = ClickHouseConfiguration.class)
@Factory
public class ClickHouseNativeFactory {

    private final ClickHouseDriver driver;

    public ClickHouseNativeFactory() {
        this.driver = new ClickHouseDriver();
    }

    public ClickHouseConnection getConnection(String jdbcUrl, Properties properties) {
        try {
            return (ClickHouseConnection) driver.connect(jdbcUrl, properties);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public ClickHouseConnection getConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration.getJDBC(), configuration.getProperties().asProperties());
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Prototype
    @Named("prototype")
    protected ClickHouseConnection getPrototypeConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration);
    }
}
