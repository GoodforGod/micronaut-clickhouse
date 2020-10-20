package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
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
public class ClickHouseConfiguration extends AbstractClickHouseConfiguration {

    @ConfigurationBuilder(prefixes = "set")
    private final ClickHouseProperties properties;

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

    /**
     * @return JDBC connections url for ClickHouse driver
     */
    public String getJDBC() {
        return getJDBC(properties);
    }

    /**
     * @return HTTP url for ClickHouse server
     */
    public String getURL() {
        return getURL(properties);
    }

    @Override
    public String toString() {
        Properties properties = this.properties.asProperties();
        properties.put("createDatabaseIfNotExist", createDatabaseIfNotExist);
        return properties.toString();
    }
}
