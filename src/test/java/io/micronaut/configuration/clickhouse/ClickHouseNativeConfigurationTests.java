package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.settings.SettingKey;
import io.micronaut.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
class ClickHouseNativeConfigurationTests extends ClickhouseRunner {

    @Test
    void createWithCorrectDatabaseAsConfigured() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.native.port", 9999);
        properties.put("clickhouse.database", "custom");

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseNativeConfiguration configuration = context.getBean(ClickHouseNativeConfiguration.class);
        final Properties props = configuration.asProperties();
        assertNotNull(configuration.toString());

        assertEquals(9999, props.get(SettingKey.port.name()));
        assertEquals("127.0.0.1", props.get(SettingKey.host.name()));
        assertEquals("custom", props.get(SettingKey.database.name()));

        assertNotNull(configuration.getUrl());
        assertTrue(configuration.getUrl().contains("127.0.0.1"));
        assertTrue(configuration.getUrl().contains("custom"));
    }

    @Test
    void createWithWrongCorrectDatabase() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.native.database", "native");
        properties.put("clickhouse.native.address", "localhost");
        properties.put("clickhouse.native.port", 9001);
        properties.put("clickhouse.port", 9000);
        properties.put("clickhouse.database", "official");
        properties.put("clickhouse.async", false);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseNativeConfiguration configuration = context.getBean(ClickHouseNativeConfiguration.class);
        final Properties props = configuration.asProperties();

        assertEquals(9001, props.get(SettingKey.port.name()));
        assertEquals("127.0.0.1", props.get(SettingKey.host.name()));
        assertEquals("native", props.get(SettingKey.database.name()));

        final ClickHouseConfiguration configurationOfficial = context.getBean(ClickHouseConfiguration.class);
        final ClickHouseProperties propsOfficial = configurationOfficial.getProperties();

        assertEquals("official", propsOfficial.getDatabase());
        assertFalse(propsOfficial.isAsync());
        assertEquals(9000, propsOfficial.getPort());
    }
}
