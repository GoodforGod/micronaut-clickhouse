package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.clickhouse.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.sql.SQLException;

/**
 * Default factory for creating Official ClickHouse client
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseDriver.class)
@Requires(beans = ClickHouseClientConfiguration.class)
@Factory
public class ClickHouseClientFactory {

    @Named(ClickHouseSettings.QUALIFIER)
    @Bean
    @Singleton
    public ClickHouseClient getClickHouseClient(ClickHouseClientConfiguration configuration) {
        return ClickHouseClient.builder()
                .config(configuration.getConfig())
                .build();
    }
}
