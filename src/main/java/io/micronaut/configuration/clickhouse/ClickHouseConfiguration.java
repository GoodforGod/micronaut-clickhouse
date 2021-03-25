package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.util.StringUtils;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    @ConfigurationBuilder("health")
    private final EnableConfiguration health = new EnableConfiguration(true);

    private boolean createDatabaseIfNotExist = false;
    private int createDatabaseTimeoutInMillis = 10000;

    private String jdbcUrl;

    /**
     * User {@link #jdbcUrl} as provided without {@link #properties}
     */
    private boolean useRawJdbcUrl = true;

    /**
     * New props to init default values
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
        return StringUtils.isEmpty(jdbcUrl)
                ? getJdbcUrl(properties.getHost(), properties.getPort(), properties.getDatabase(), properties.asProperties())
                : jdbcUrl;
    }

    public URI getURI() {
        return (properties.getSsl())
                ? URI.create(String.format("https://%s:%s", properties.getHost(), properties.getPort()))
                : URI.create(String.format("http://%s:%s", properties.getHost(), properties.getPort()));
    }

    public EnableConfiguration getHealth() {
        return health;
    }

    public int getCreateDatabaseTimeoutInMillis() {
        return createDatabaseTimeoutInMillis;
    }

    public void setCreateDatabaseTimeoutInMillis(int createDatabaseTimeoutInMillis) {
        this.createDatabaseTimeoutInMillis = createDatabaseTimeoutInMillis;
    }

    public void setJdbcUrl(String jdbcUrl) {
        try {
            final List<String> urls = splitUrl(jdbcUrl);
            final String firstJdbcUrl = urls.get(0);
            final ClickHouseProperties urlProperties = ClickhouseJdbcUrlParser.parse(firstJdbcUrl, this.properties.asProperties());
            this.properties.merge(urlProperties);

            if(isUseRawJdbcUrl()) {
                this.jdbcUrl = jdbcUrl;
                return;
            }

            final int propsStartFrom = jdbcUrl.indexOf("?");
            this.jdbcUrl = (propsStartFrom == -1)
                    ? jdbcUrl + getJdbcProperties(properties.asProperties())
                    : jdbcUrl.substring(0, propsStartFrom) + getJdbcProperties(properties.asProperties());
        } catch (URISyntaxException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    public boolean isUseRawJdbcUrl() {
        return useRawJdbcUrl;
    }

    public void setUseRawJdbcUrl(boolean useRawJdbcUrl) {
        this.useRawJdbcUrl = useRawJdbcUrl;
    }

    @Override
    public String toString() {
        return this.properties.asProperties().toString();
    }
}
