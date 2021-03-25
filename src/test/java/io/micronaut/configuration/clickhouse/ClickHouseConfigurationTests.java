package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
class ClickHouseConfigurationTests extends ClickhouseRunner {

    @Test
    void createWithCorrectDatabaseAsConfigured() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", 9999);
        properties.put("clickhouse.ssl", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConfiguration configuration = context.getBean(ClickHouseConfiguration.class);
        final ClickHouseProperties props = configuration.getProperties();
        assertNotNull(configuration.toString());
        assertFalse(configuration.isCreateDatabaseIfNotExist());
        assertEquals(10000, configuration.getCreateDatabaseTimeoutInMillis());
        assertNotNull(configuration.getHealth());
        assertTrue(configuration.getHealth().isEnabled());

        assertEquals(9999, props.getPort());
        assertEquals("127.0.0.1", props.getHost());
        assertEquals("default", props.getDatabase());
        assertFalse(props.isAsync());

        assertNotNull(configuration.getUrl());
        assertTrue(configuration.getUrl().contains("127.0.0.1"));
        assertTrue(configuration.getUrl().contains("default"));
    }

    @Test
    void createWithWrongCorrectDatabase() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");
        properties.put("clickhouse.port", 9001);
        properties.put("clickhouse.async", true);

        final ApplicationContext context = ApplicationContext.run(properties);

        final ClickHouseConfiguration configuration = context.getBean(ClickHouseConfiguration.class);
        final ClickHouseProperties props = configuration.getProperties();

        assertEquals(9001, props.getPort());
        assertEquals("localhost", props.getHost());
        assertEquals("custom", props.getDatabase());
        assertTrue(props.isAsync());
    }
}
