package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.Properties;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX)
@Requires(classes = ClickHouseConnection.class)
@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseConfiguration extends ClickHouseAbstractConfiguration {

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
    }

    public ClickHouseProperties getProperties() {
        return properties;
    }

    public String getURL() {
        return buildURL(properties.getHost(), properties.getPort(), properties.getDatabase());
    }
}
