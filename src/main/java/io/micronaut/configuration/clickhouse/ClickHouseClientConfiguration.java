package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseConfig;
import com.clickhouse.client.ClickHouseCredentials;
import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;

import java.util.Map;
import java.util.Properties;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @see com.clickhouse.client.config.ClickHouseClientOption
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseDriver.class)
@Requires(property = ClickHouseSettings.PREFIX + ".client")
@ConfigurationProperties(ClickHouseSettings.PREFIX + ".client")
public class ClickHouseClientConfiguration {

    @ConfigurationBuilder("properties")
    private final ClickHouseClientOptions properties = new ClickHouseClientOptions();

    @ConfigurationBuilder("credentials")
    private final ClickHouseClientCredentials credentials = new ClickHouseClientCredentials();

    public ClickHouseClientOptions getProperties() {
        return properties;
    }

    public ClickHouseClientCredentials getCredentials() {
        return credentials;
    }

    public ClickHouseConfig getConfig() {
        return new ClickHouseConfig(ClickHouseConfig.toClientOptions(properties.getAsMap()), credentials.getCredentials(), null, null);
    }

    @Override
    public String toString() {
        return properties.toString();
    }
}
