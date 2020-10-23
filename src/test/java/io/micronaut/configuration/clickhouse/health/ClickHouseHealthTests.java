package io.micronaut.configuration.clickhouse.health;

import io.micronaut.context.ApplicationContext;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthResult;
import io.reactivex.Single;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseHealthTests extends Assertions {

    @Container
    private final ClickHouseContainer container = new ClickHouseContainer();

    @Test
    void checkHealthUp() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthIndicator indicator = context.getBean(ClickHouseHealthIndicator.class);

        final HealthResult result = Single.fromPublisher(indicator.getResult()).timeout(60, TimeUnit.SECONDS).blockingGet();
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

        final HealthResult result = Single.fromPublisher(indicator.getResult()).timeout(60, TimeUnit.SECONDS).blockingGet();
        assertEquals(HealthStatus.DOWN, result.getStatus());
        assertEquals("clickhouse", result.getName());
        assertTrue(result.getDetails() instanceof Map);
        assertNotNull(((Map<?, ?>) result.getDetails()).get("httpCode"));
        assertNotNull(((Map<?, ?>) result.getDetails()).get("body"));
        assertNotNull(result.getDetails());
    }

    @Test
    void checkHealthDown() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", 9091);
        properties.put("clickhouse.async", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseHealthIndicator indicator = context.getBean(ClickHouseHealthIndicator.class);

        final HealthResult result = Single.fromPublisher(indicator.getResult()).timeout(60, TimeUnit.SECONDS).blockingGet();
        assertEquals(HealthStatus.DOWN, result.getStatus());
        assertEquals("clickhouse", result.getName());
    }
}
