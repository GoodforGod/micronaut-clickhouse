package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Default factory for creating Native ClickHouse client
 * {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.3.2020
 */
@Requires(beans = ClickHouseNativeConfiguration.class)
@Requires(classes = com.github.housepower.jdbc.ClickHouseConnection.class)
@Factory
public class ClickHouseNativeFactory {

    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public com.github.housepower.jdbc.ClickHouseConnection getConnection(ClickHouseNativeConfiguration configuration) {
        try {
            return ClickHouseConnection.createClickHouseConnection(configuration.getConfig());
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Bean(preDestroy = "close")
    @Prototype
    @Named("prototype")
    protected com.github.housepower.jdbc.ClickHouseConnection getPrototypeConnection(ClickHouseNativeConfiguration configuration) {
        return getConnection(configuration);
    }
}
