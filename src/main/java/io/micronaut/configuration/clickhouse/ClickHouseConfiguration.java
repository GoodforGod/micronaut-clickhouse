package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ConfigurationBuilder(prefixes = "set")
    private ClickHouseProperties properties;

    private boolean createDatabaseIfNotExist = false;

    /**
     * new props to init default values
     */
    public ClickHouseConfiguration() {
        this.properties = new ClickHouseProperties(new Properties());
        this.properties.setHost(ClickHouseSettings.DEFAULT_HOST);
        this.properties.setPort(ClickHouseSettings.DEFAULT_PORT);
        this.properties.setDatabase(ClickHouseSettings.DEFAULT_DATABASE);
    }

    /**
     * Initialize new configuration with new properties
     * 
     * @param properties to init with
     */
    public ClickHouseConfiguration(ClickHouseProperties properties) {
        this.properties = new ClickHouseProperties(properties);
    }

    /**
     * @return whenever to create database on context initialization
     */
    public boolean isCreateDatabaseIfNotExist() {
        return createDatabaseIfNotExist;
    }

    /**
     * @param createDatabaseIfNotExist indicates to create database if not exist
     *                                 while context initialization
     */
    public void setCreateDatabaseIfNotExist(boolean createDatabaseIfNotExist) {
        this.createDatabaseIfNotExist = createDatabaseIfNotExist;
    }

    public ClickHouseProperties getProperties() {
        return properties;
    }

    public String getJDBC() {
        return buildURL(properties.getHost(), properties.getPort(), properties.getDatabase());
    }

    public String getURL() {
        return (properties.getSsl())
                ? String.format("https://%s:%s", properties.getHost(), properties.getPort())
                : String.format("http://%s:%s", properties.getHost(), properties.getPort());
    }

    public String buildURL(String host, int port, String database) {
        if (StringUtils.isEmpty(host))
            throw new IllegalArgumentException("ClickHouse Host is empty!");

        if (StringUtils.isEmpty(database))
            throw new IllegalArgumentException("ClickHouse Database is empty!");

        final String url = String.format("%s//%s:%s/%s", ClickhouseJdbcUrlParser.JDBC_CLICKHOUSE_PREFIX, host, port, database);
        logger.debug("ClickHouse URL: {}", url);
        return url;
    }
}
