package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.settings.ClickHouseConfig;
import com.github.housepower.jdbc.settings.SettingKey;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.util.StringUtils;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseNativeConfiguration extends AbstractClickHouseConfiguration {

    /**
     * Native properties to set via micronaut map property builder
     */
    @MapFormat(transformation = MapFormat.MapTransformation.FLAT)
    private final Map<String, Object> properties = new HashMap<>(30);

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
        clickHouseProperties.asProperties().forEach((k, v) -> {
            if (StringUtils.isNotEmpty(v.toString()))
                properties.put(String.valueOf(k), String.valueOf(v));
        });

        this.properties.put(SettingKey.address.name(), clickHouseProperties.getHost());
        this.properties.put(SettingKey.port.name(), ClickHouseSettings.DEFAULT_NATIVE_PORT);
        this.properties.put(SettingKey.database.name(), clickHouseProperties.getDatabase());

        // in sec
        this.properties.put(SettingKey.http_receive_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30));
        // in sec
        this.properties.put(SettingKey.http_send_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30));
        // in sec
        this.properties.put(SettingKey.connect_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30));
        // in sec multiply 1000 in config
        this.properties.put(SettingKey.query_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 10000, 10));
        // in millis
        this.properties.put(SettingKey.connect_timeout_with_failover_ms.name(), clickHouseProperties.getConnectionTimeout());
        this.properties.put(SettingKey.max_read_buffer_size.name(), clickHouseProperties.getBufferSize());
        this.properties.put(SettingKey.use_client_time_zone.name(), !clickHouseProperties.isUseServerTimeZone());
        this.properties.put(SettingKey.insert_distributed_timeout.name(), clickHouseProperties.getDataTransferTimeout());
        if (clickHouseProperties.getMaxThreads() != null)
            this.properties.put(SettingKey.max_threads.name(), clickHouseProperties.getMaxThreads());
    }

    protected void setNative(Map<String, Object> properties) {
        this.properties.putAll(properties);
    }

    /**
     * @return properties for native ClickHouse driver
     */
    public Properties getProperties() {
        final Properties properties = new Properties();
        this.properties.forEach(properties::put);
        return properties;
    }

    /**
     * @param key for property to retrieve
     * @return property value for given key
     */
    public Optional<String> getProperty(SettingKey key) {
        return Optional.ofNullable(properties.get(key.name())).map(Object::toString);
    }

    /**
     * @return JDBC connections url for ClickHouse driver
     */
    public String getJDBC() {
        final String host = getProperty(SettingKey.address)
                .orElseThrow(() -> new ConfigurationException("ClickHouse Native Host is not specified!"));
        final String port = getProperty(SettingKey.port)
                .orElseThrow(() -> new ConfigurationException("ClickHouse Native Port is not specified!"));
        final String database = getProperty(SettingKey.database)
                .orElseThrow(() -> new ConfigurationException("ClickHouse Native Database is not specified!"));
        return getJDBC(host, Integer.parseInt(port), database);
    }

    /**
     * @return HTTP url for ClickHouse server
     */
    public String getURL() {
        final String host = getProperty(SettingKey.address)
                .orElseThrow(() -> new ConfigurationException("ClickHouse Native Host is not specified!"));
        final String port = getProperty(SettingKey.port)
                .orElseThrow(() -> new ConfigurationException("ClickHouse Native Port is not specified!"));
        return getURL(host, Integer.parseInt(port), false);
    }

    /**
     * @return ClickHouse Native drive configuration for connection
     */
    public ClickHouseConfig getConfig() {
        try {
            return new ClickHouseConfig(getJDBC(), getProperties());
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String toString() {
        return properties.toString();
    }
}
