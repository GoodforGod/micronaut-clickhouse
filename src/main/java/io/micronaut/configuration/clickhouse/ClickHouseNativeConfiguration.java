package io.micronaut.configuration.clickhouse;

import com.github.housepower.settings.ClickHouseConfig;
import com.github.housepower.settings.SettingKey;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * ClickHouse Native Driver
 * {@link com.github.housepower.jdbc.ClickHouseConnection} configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 2.4.2020
 */
@Requires(property = ClickHouseSettings.PREFIX_NATIVE)
@Requires(beans = ClickHouseConfiguration.class)
@ConfigurationProperties(ClickHouseSettings.PREFIX_NATIVE)
public class ClickHouseNativeConfiguration extends AbstractClickHouseConfiguration {

    @ConfigurationBuilder(prefixes = "set")
    private final ClickhouseNativeProperties properties = new ClickhouseNativeProperties();

    private String url;
    private String rawUrl;

    /**
     * User {@link #url} as provided without {@link #properties}
     */
    private boolean useRawUrl = true;

    /**
     * Setups default non native configs for native configurations as some of them
     * interlope This can provide better default experience without much configuring
     * same properties for native and non native drivers
     *
     * @param configuration to get default non native properties from
     */
    @Inject
    public ClickHouseNativeConfiguration(ClickHouseConfiguration configuration) {
        final ClickHouseProperties clickHouseProperties = configuration.getProperties();

        this.properties.setHost(clickHouseProperties.getHost());
        this.properties.setPort(ClickHouseSettings.DEFAULT_NATIVE_PORT);
        this.properties.setDatabase(clickHouseProperties.getDatabase());
        this.properties.setUser(clickHouseProperties.getUser());
        this.properties.setPassword(clickHouseProperties.getPassword());

        // in sec
        this.properties.setConnectTimeout(Duration.ofSeconds(Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30)));
        // in sec multiply 1000 in config
        this.properties.setQueryTimeout(Duration.ofSeconds(Math.max(clickHouseProperties.getConnectionTimeout() / 10000, 10)));
        this.properties.setUseClientTimeZone(clickHouseProperties.isUseServerTimeZone());
        this.properties.setMaxThreads(clickHouseProperties.getMaxThreads());
    }

    /**
     * @return properties for native ClickHouse driver
     */
    public Properties asProperties() {
        final Properties properties = new Properties();
        this.properties.asSettings().forEach((k, v) -> properties.put(k.name(), v));
        return properties;
    }

    /**
     * @return connection url for ClickHouse
     */
    public String getUrl() {
        if (StringUtils.isEmpty(url)) {
            final Properties props = this.properties.asProperties();
            props.remove(SettingKey.host.name());
            props.remove(SettingKey.port.name());
            props.remove(SettingKey.database.name());
            return getJdbcUrl(properties.getHost(), properties.getPort(), properties.getDatabase(), props);
        }

        return isUseRawUrl()
                ? rawUrl
                : url;
    }

    public void setUrl(String url) {
        this.rawUrl = url;

        final List<String> urls = splitUrl(url);
        final String firstJdbcUrl = urls.get(0);
        final ClickHouseConfig config = ClickHouseConfig.Builder.builder().withJdbcUrl(firstJdbcUrl).build();
        config.settings().forEach(properties::addSettings);
        final Properties props = this.properties.asProperties();
        props.remove(SettingKey.host.name());
        props.remove(SettingKey.port.name());
        props.remove(SettingKey.database.name());
        final int propsStartFrom = url.indexOf("?");
        this.url = (propsStartFrom == -1)
                ? url + getJdbcProperties(props)
                : url.substring(0, propsStartFrom) + getJdbcProperties(props);
    }

    public boolean isUseRawUrl() {
        return useRawUrl;
    }

    public void setUseRawUrl(boolean useRawUrl) {
        this.useRawUrl = useRawUrl;
    }

    /**
     * @return ClickHouse Native drive configuration for connection
     */
    public ClickHouseConfig getConfig() {
        return ClickHouseConfig.Builder.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .user(properties.getUser())
                .password(properties.getPassword())
                .charset(properties.getCharset())
                .connectTimeout(properties.getConnectTimeout())
                .withSettings(properties.asSettings())
                .build();
    }

    public ClickhouseNativeProperties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return properties.asSettings().toString();
    }
}
