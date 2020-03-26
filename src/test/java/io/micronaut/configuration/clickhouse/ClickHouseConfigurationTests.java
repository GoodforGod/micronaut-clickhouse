package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseConfigurationTests extends Assertions {

    @Container
    private final ClickHouseContainer container = new ClickHouseContainer();

    @Test
    void createWithCorrectDatabaseAsConfigured() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getFirstMappedPort());

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConfiguration configuration = context.getBean(ClickHouseConfiguration.class);
        final ClickHouseProperties props = configuration.getProperties();

        assertEquals(container.getFirstMappedPort(), props.getPort());
        assertEquals("127.0.0.1", props.getHost());
        assertEquals("default", props.getDatabase());
        assertFalse(props.isAsync());
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
