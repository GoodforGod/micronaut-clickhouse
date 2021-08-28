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
import java.time.Duration;
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

    private boolean createDatabaseIfNotExist = false;
    private Duration createDatabaseTimeout = Duration.ofSeconds(10);

    private String url;
    private String rawUrl;

    /**
     * User {@link #url} as provided without {@link #properties}
     */
    private boolean useRawUrl = true;

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
     * @return connection url for ClickHouse
     */
    public String getUrl() {
        if (StringUtils.isEmpty(url))
            return getJdbcUrl(properties.getHost(), properties.getPort(), properties.getDatabase(), properties.asProperties());

        return isUseRawUrl() ? rawUrl : url;
    }

    public URI getURI() {
        return (properties.getSsl())
                ? URI.create(String.format("https://%s:%s", properties.getHost(), properties.getPort()))
                : URI.create(String.format("http://%s:%s", properties.getHost(), properties.getPort()));
    }

    public Duration getCreateDatabaseTimeout() {
        return createDatabaseTimeout;
    }

    public void setCreateDatabaseTimeout(Duration createDatabaseTimeout) {
        if (createDatabaseTimeout == null)
            return;
        if (createDatabaseTimeout.isNegative())
            throw new ConfigurationException("CreateDatabaseTimeout can not be less than 0");
        this.createDatabaseTimeout = createDatabaseTimeout;
    }

    public void setUrl(String url) {
        this.rawUrl = url;
        try {
            final List<String> urls = splitUrl(url);
            final String firstJdbcUrl = urls.get(0);
            final ClickHouseProperties urlProperties = ClickhouseJdbcUrlParser.parse(firstJdbcUrl, this.properties.asProperties());
            this.properties.merge(urlProperties);
            final int propsStartFrom = url.indexOf("?");
            this.url = (propsStartFrom == -1)
                    ? url + getJdbcProperties(properties.asProperties())
                    : url.substring(0, propsStartFrom) + getJdbcProperties(properties.asProperties());
        } catch (URISyntaxException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    public boolean isUseRawUrl() {
        return useRawUrl;
    }

    public void setUseRawUrl(boolean useRawUrl) {
        this.useRawUrl = useRawUrl;
    }

    @Override
    public String toString() {
        return this.properties.asProperties().toString();
    }
}
