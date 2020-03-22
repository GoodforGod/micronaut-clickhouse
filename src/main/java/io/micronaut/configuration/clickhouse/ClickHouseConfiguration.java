package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.Properties;

/**
 * ClickHouse official driver configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX)
@Requires(classes = ClickHouseConnection.class)
@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ConfigurationBuilder(prefixes = "set")
    private ClickHouseProperties properties;

    /**
     * new props to init default values
     */
    public ClickHouseConfiguration() {
        this.properties = new ClickHouseProperties(new Properties());
        this.properties.setHost(ClickHouseSettings.DEFAULT_HOST);
        this.properties.setPort(ClickHouseSettings.DEFAULT_PORT);
        this.properties.setDatabase(ClickHouseSettings.DEFAULT_DATABASE);
        logger.debug("Settings default HOST '{}', PORT '{}', DATABASE '{}' values for configuration",
                ClickHouseSettings.DEFAULT_HOST, ClickHouseSettings.DEFAULT_PORT, ClickHouseSettings.DEFAULT_DATABASE);
    }

    public ClickHouseProperties getProperties() {
        return properties;
    }

    public String getURL() {
        final String host = properties.getHost();
        final int port = properties.getPort();
        final String database = properties.getDatabase();

        if(StringUtils.isEmpty(host))
            throw new IllegalArgumentException("ClickHouse Host is empty!");

        if(StringUtils.isEmpty(database))
            throw new IllegalArgumentException("ClickHouse Database is empty!");

        final String url = "jdbc:clickhouse://" + host + ":" + port + "/" + database;
        logger.debug("ClickHouse URL: {}", url);
        return url;
    }
}
