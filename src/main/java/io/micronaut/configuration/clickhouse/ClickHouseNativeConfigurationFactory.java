package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.settings.ClickHouseConfig;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.runtime.context.scope.Refreshable;

import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX)
@Requires(classes = ClickHouseConnection.class)
@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseNativeConfigurationFactory {

    @Refreshable(ClickHouseSettings.PREFIX)
    @Singleton
    @Primary
    public ClickHouseConfig config(ClickHouseConfiguration configuration) {
        try {
            return new ClickHouseConfig(configuration.getJDBC(), configuration.getProperties().asProperties());
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }
}
