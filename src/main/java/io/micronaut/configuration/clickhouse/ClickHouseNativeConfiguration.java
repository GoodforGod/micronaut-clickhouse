package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.settings.ClickHouseConfig;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Properties;

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
    private final ClickhouseNativeProperties nativeProperties = new ClickhouseNativeProperties();

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

        this.nativeProperties.setHost(clickHouseProperties.getHost());
        this.nativeProperties.setPort(ClickHouseSettings.DEFAULT_NATIVE_PORT);
        this.nativeProperties.setDatabase(clickHouseProperties.getDatabase());
        this.nativeProperties.setUser(clickHouseProperties.getUser());
        this.nativeProperties.setPassword(clickHouseProperties.getPassword());

        // in sec
        this.nativeProperties.setConnectTimeout(Duration.ofSeconds(Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30)));
        // in sec multiply 1000 in config
        this.nativeProperties.setQueryTimeout(Duration.ofSeconds(Math.max(clickHouseProperties.getConnectionTimeout() / 10000, 10)));
        this.nativeProperties.setUseClientTimeZone(clickHouseProperties.isUseServerTimeZone());
        this.nativeProperties.setMaxThreads(clickHouseProperties.getMaxThreads());
    }

    /**
     * @return properties for native ClickHouse driver
     */
    public Properties getProperties() {
        final Properties properties = new Properties();
        this.nativeProperties.getSettings().forEach((k, v) -> properties.put(k.name(), v));
        return properties;
    }

    /**
     * @return JDBC connections url for ClickHouse driver
     */
    public String getJDBC() {
        return getConfig().jdbcUrl();
    }

    /**
     * @return ClickHouse Native drive configuration for connection
     */
    public ClickHouseConfig getConfig() {
        return ClickHouseConfig.Builder.builder()
                .host(nativeProperties.getHost())
                .port(nativeProperties.getPort())
                .database(nativeProperties.getDatabase())
                .user(nativeProperties.getUser())
                .password(nativeProperties.getPassword())
                .charset(nativeProperties.getCharset())
                .connectTimeout(nativeProperties.getConnectTimeout())
                .withSettings(nativeProperties.getSettings())
                .build();
    }

    public ClickhouseNativeProperties getNativeProperties() {
        return nativeProperties;
    }

    @Override
    public String toString() {
        return nativeProperties.getSettings().toString();
    }
}
