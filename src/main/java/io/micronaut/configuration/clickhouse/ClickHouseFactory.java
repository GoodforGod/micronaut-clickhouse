package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.runtime.context.scope.Refreshable;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Default factory for creating Official ClickHouse client
 * {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(property = "clic.on", value = "true")
@Requires(beans = ClickHouseConfiguration.class)
@Requires(classes = ru.yandex.clickhouse.ClickHouseConnection.class)
@Factory
public class ClickHouseFactory {

    private final ru.yandex.clickhouse.ClickHouseDriver driver;

    public ClickHouseFactory() {
        this.driver = new ClickHouseDriver();
    }

    public ru.yandex.clickhouse.ClickHouseConnection getConnection(String jdbcUrl, ClickHouseProperties properties) {
        try {
            return driver.connect(jdbcUrl, properties);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public ru.yandex.clickhouse.ClickHouseConnection getConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration.getJDBC(), configuration.getProperties());
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Prototype
    @Named("prototype")
    protected ru.yandex.clickhouse.ClickHouseConnection getPrototypeConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration);
    }
}
