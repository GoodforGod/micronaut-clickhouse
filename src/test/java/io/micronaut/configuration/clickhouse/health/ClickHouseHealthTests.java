package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickhouseRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthResult;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseHealthTests extends ClickhouseRunner {

    @Container
    private final ClickHouseContainer container = getContainer();

    @Test
    void checkHealthUp() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthIndicator indicator = context.getBean(ClickHouseHealthIndicator.class);

        final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
        assertEquals(HealthStatus.UP, result.getStatus());
        assertEquals("clickhouse", result.getName());
        assertNotNull(result.getDetails());
    }

    @Test
    void checkHealthDownBadRequest() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.NATIVE_PORT));
        properties.put("clickhouse.async", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthIndicator indicator = context.getBean(ClickHouseHealthIndicator.class);

        final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
        assertEquals(HealthStatus.DOWN, result.getStatus());
        assertEquals("clickhouse", result.getName());
        assertTrue(result.getDetails() instanceof Map);
        assertNotNull(((Map<?, ?>) result.getDetails()).get("error"));
        assertNotNull(result.getDetails());
    }

    @Test
    void checkHealthDown() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", 9091);
        properties.put("clickhouse.async", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthIndicator indicator = context.getBean(ClickHouseHealthIndicator.class);

        final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
        assertEquals(HealthStatus.DOWN, result.getStatus());
        assertEquals("clickhouse", result.getName());
    }
}
