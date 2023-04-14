package io.micronaut.configuration.clickhouse;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.clickhouse.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.sql.SQLException;

/**
 * Default factory for creating Official ClickHouse client
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseDriver.class)
@Requires(beans = ClickHouseJdbcConfiguration.class)
@Factory
public class ClickHouseJdbcDataSourceFactory {

    @Named(ClickHouseSettings.QUALIFIER)
    @Bean
    @Singleton
    public ClickHouseDataSource getClickHouseDataSource(ClickHouseJdbcConfiguration configuration) {
        try {
            return new ClickHouseDataSource(configuration.getUrl(), configuration.getProperties());
        } catch (SQLException e) {
            throw new ConfigurationException("Can't configure ClickHouseDataSource", e);
        }
    }
}
