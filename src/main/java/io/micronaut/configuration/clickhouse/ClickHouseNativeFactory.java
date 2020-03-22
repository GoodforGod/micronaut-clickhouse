package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.runtime.context.scope.Refreshable;

import javax.inject.Singleton;

/**
 * Default factory for creating Native ClickHouse client {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(beans = ClickHouseNativeConfiguration.class)
@Factory
public class ClickHouseNativeFactory {

    @Refreshable(ClickHouseSettings.PREFIX)
    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public ClickHouseConnection getConnection(ClickHouseNativeConfiguration configuration) {
        return null;
    }
}
