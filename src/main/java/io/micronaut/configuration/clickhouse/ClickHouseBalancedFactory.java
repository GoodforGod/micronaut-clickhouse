package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;

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
        final String jdbc = configuration.getUrl();
        return new BalancedClickhouseDataSource(jdbc);
    }
}
