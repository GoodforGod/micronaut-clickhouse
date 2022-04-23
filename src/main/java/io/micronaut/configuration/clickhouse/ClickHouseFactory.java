package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.sql.SQLException;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * Default factory for creating Official ClickHouse client
 * {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(beans = ClickHouseConfiguration.class)
@Requires(classes = ClickHouseConnection.class)
@Factory
public class ClickHouseFactory {

    private final ClickHouseDriver driver = new ClickHouseDriver();

    public ClickHouseConnection getConnection(String jdbcUrl, ClickHouseProperties properties) {
        try {
            return driver.connect(jdbcUrl, properties);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Named("clickhouse-singleton")
    @Bean(preDestroy = "close")
    @Singleton
    public ClickHouseConnection getConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration.getUrl(), configuration.getProperties());
    }

    @Primary
    @Named("clickhouse")
    @Bean(preDestroy = "close")
    @Prototype
    protected ClickHouseConnection getPrototypeConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration.getUrl(), configuration.getProperties());
    }
}
