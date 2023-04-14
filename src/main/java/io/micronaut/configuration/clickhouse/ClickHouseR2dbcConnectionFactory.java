package io.micronaut.configuration.clickhouse;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.clickhouse.r2dbc.connection.ClickHouseConnectionFactory;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Default factory for creating Official ClickHouse client
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseConnectionFactory.class)
@Requires(beans = ClickHouseR2dbcConfiguration.class)
@Factory
public class ClickHouseR2dbcConnectionFactory {

    @Named(ClickHouseSettings.QUALIFIER)
    @Bean
    @Singleton
    public ConnectionFactory getConnectionFactory(ClickHouseR2dbcConfiguration configuration) {
        return ConnectionFactories.get(configuration.getUrl());
    }
}
