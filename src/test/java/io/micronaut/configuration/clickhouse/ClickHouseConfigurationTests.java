package io.micronaut.configuration.clickhouse;

import io.micronaut.configuration.clickhouse.health.ClickHouseHealthConfiguration;
import io.micronaut.configuration.clickhouse.health.ClickHouseHealthIndicator;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.exceptions.ConfigurationException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

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
        final ClickHouseHealthConfiguration healthConfiguration = context.getBean(ClickHouseHealthConfiguration.class);
        final ClickHouseProperties props = configuration.getProperties();
        assertNotNull(configuration.toString());
        assertFalse(configuration.isCreateDatabaseIfNotExist());
        assertEquals(Duration.ofSeconds(10), configuration.getCreateDatabaseTimeout());
        assertNotNull(healthConfiguration);
        assertTrue(healthConfiguration.isEnabled());
        assertEquals(2, healthConfiguration.getRetry());
        assertEquals(Duration.ofSeconds(10), healthConfiguration.getTimeout());

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
        properties.put("clickhouse.health.timeout", "100s");
        properties.put("clickhouse.health.retry", 1);

        final ApplicationContext context = ApplicationContext.run(properties);

        final ClickHouseConfiguration configuration = context.getBean(ClickHouseConfiguration.class);
        final ClickHouseProperties props = configuration.getProperties();

        assertEquals(9001, props.getPort());
        assertEquals("localhost", props.getHost());
        assertEquals("custom", props.getDatabase());
        assertTrue(props.isAsync());
    }

    @Test
    void createHealthRetryInvalid() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");
        properties.put("clickhouse.health.timeout", "100s");
        properties.put("clickhouse.health.retry", -1);

        try (ApplicationContext context = ApplicationContext.run(properties);) {
            context.getBean(ClickHouseHealthIndicator.class);
            fail("Should bot happen");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof ConfigurationException);
        }
    }

    @Test
    void createHealthTimeoutInvalid() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");
        properties.put("clickhouse.health.timeout", "-100s");
        properties.put("clickhouse.health.retry", 1);

        try (ApplicationContext context = ApplicationContext.run(properties);) {
            context.getBean(ClickHouseHealthIndicator.class);
            fail("Should bot happen");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof ConfigurationException);
        }
    }

    @Test
    void createDatabaseTimeoutInvalid() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");
        properties.put("clickhouse.create-database-timeout", "-10ms");

        try (ApplicationContext context = ApplicationContext.run(properties);) {
            context.getBean(ClickHouseConfiguration.class);
            fail("Should bot happen");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof ConfigurationException);
        }
    }

    @Test
    void createHealthTimeoutSkipped() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthConfiguration configuration = context.getBean(ClickHouseHealthConfiguration.class);
        configuration.setTimeout(null);
        assertEquals(Duration.ofSeconds(10), configuration.getTimeout());
    }

    @Test
    void createDatabaseTimeoutSkipped() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.host", "localhost");
        properties.put("clickhouse.create-database-timeout", "1ms");

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConfiguration configuration = context.getBean(ClickHouseConfiguration.class);
        configuration.setCreateDatabaseTimeout(null);
        assertEquals(Duration.ofMillis(1), configuration.getCreateDatabaseTimeout());
    }
}
