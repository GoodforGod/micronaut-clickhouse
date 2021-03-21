package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.settings.ClickHouseConfig;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.runtime.context.scope.Refreshable;

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
@Requires(classes = ClickHouseConnection.class)
@Factory
public class ClickHouseNativeFactory {

    @Refreshable(ClickHouseSettings.PREFIX_NATIVE)
    @Named("clickhouse-native-singleton")
    @Bean(preDestroy = "close")
    @Singleton
    public ClickHouseConnection getConnection(ClickHouseNativeConfiguration configuration) {
        try {
            final ClickHouseConfig config = configuration.getConfig();
            return ClickHouseConnection.createClickHouseConnection(config);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Primary
    @Refreshable(ClickHouseSettings.PREFIX_NATIVE)
    @Named("clickhouse-native")
    @Bean(preDestroy = "close")
    @Prototype
    protected ClickHouseConnection getPrototypeConnection(ClickHouseNativeConfiguration configuration) {
        return getConnection(configuration);
    }
}
