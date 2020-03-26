package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.runtime.context.scope.Refreshable;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;

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
@Requires(beans = ClickHouseConfiguration.class)
@Factory
public class ClickHouseFactory {

    private final ClickHouseDriver driver;

    public ClickHouseFactory() {
        this.driver = new ClickHouseDriver();
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public ClickHouseConnection getConnection(ClickHouseConfiguration configuration) {
        try {
            return driver.connect(configuration.getJDBC(), configuration.getProperties());
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Prototype
    @Named("prototype")
    protected ClickHouseConnection getPrototypeConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration);
    }
}
