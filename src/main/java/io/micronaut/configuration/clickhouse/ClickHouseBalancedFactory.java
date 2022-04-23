package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.3.2021
 */
@Requires(beans = ClickHouseConfiguration.class)
@Requires(classes = BalancedClickhouseDataSource.class)
@Factory
public class ClickHouseBalancedFactory {

    @Named("clickhouse")
    @Bean
    @Singleton
    public BalancedClickhouseDataSource getConnection(ClickHouseConfiguration configuration) {
        final String jdbc = configuration.getUrl();
        return new BalancedClickhouseDataSource(jdbc);
    }
}
