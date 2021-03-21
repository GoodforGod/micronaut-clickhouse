package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.runtime.context.scope.Refreshable;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.3.2021
 */
@Requires(beans = ClickHouseConfiguration.class)
@Requires(classes = ru.yandex.clickhouse.BalancedClickhouseDataSource.class)
@Factory
public class ClickHouseBalancedFactory {

    @Named("clickhouse")
    @Bean
    @Singleton
    public ru.yandex.clickhouse.BalancedClickhouseDataSource getConnection(ClickHouseConfiguration configuration) {
        final String jdbc = configuration.getJDBC();
        final ClickHouseProperties properties = configuration.getProperties();
        return new BalancedClickhouseDataSource(jdbc, properties);
    }
}