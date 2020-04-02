package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.settings.ClickHouseConfig;
import com.github.housepower.jdbc.settings.SettingKey;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.ConfigurationException;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Default factory for creating Native ClickHouse client
 * {@link ClickHouseConnection}.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.3.2020
 */
@Requires(beans = ClickHouseConfiguration.class)
@Requires(classes = com.github.housepower.jdbc.ClickHouseConnection.class)
@Factory
public class ClickHouseNativeFactory {

    public com.github.housepower.jdbc.ClickHouseConnection getConnection(ClickHouseConfig clickHouseConfig) {
        try {
            return ClickHouseConnection.createClickHouseConnection(clickHouseConfig);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Bean
    public com.github.housepower.jdbc.settings.ClickHouseConfig getConfig(ClickHouseConfiguration configuration) {
        try {
            final ClickHouseProperties clickHouseProperties = configuration.getProperties();
            final Properties properties = clickHouseProperties.asProperties();
            properties.put(SettingKey.connect_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 1000, 30)); // in sec
            properties.put(SettingKey.query_timeout.name(), Math.max(clickHouseProperties.getConnectionTimeout() / 10000, 10)); // in sec multiply 1000 in config
            properties.put(SettingKey.connect_timeout_with_failover_ms.name(), clickHouseProperties.getConnectionTimeout()); // in millis
            properties.put(SettingKey.max_read_buffer_size.name(), clickHouseProperties.getBufferSize());
            properties.put(SettingKey.use_client_time_zone.name(), !clickHouseProperties.isUseServerTimeZone());
            properties.put(SettingKey.insert_distributed_timeout.name(), clickHouseProperties.getDataTransferTimeout());
            if (clickHouseProperties.getMaxThreads() != null)
                properties.put(SettingKey.max_threads.name(), clickHouseProperties.getMaxThreads());
            return new ClickHouseConfig(configuration.getJDBC(), properties);
        } catch (SQLException e) {
            throw new ConfigurationException(e.getMessage(), e.getCause());
        }
    }

    @Bean(preDestroy = "close")
    @Singleton
    @Primary
    public com.github.housepower.jdbc.ClickHouseConnection getConnection(ClickHouseConfiguration configuration) {
        return getConnection(getConfig(configuration));
    }

    @Bean(preDestroy = "close")
    @Prototype
    @Named("prototype")
    protected com.github.housepower.jdbc.ClickHouseConnection getPrototypeConnection(ClickHouseConfiguration configuration) {
        return getConnection(configuration);
    }
}
