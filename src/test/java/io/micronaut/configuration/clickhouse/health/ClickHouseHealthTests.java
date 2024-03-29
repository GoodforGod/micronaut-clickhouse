package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickhouseRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthResult;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClickHouseHealthTests extends ClickhouseRunner {

    @Container
    private static final ClickHouseContainer container = getContainer();

    @Test
    void checkJdbcHealthUp() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.jdbc.url", container.getJdbcUrl());

        try (final ApplicationContext context = ApplicationContext.run(properties)) {
            final ClickHouseJdbcHealthIndicator indicator = context.getBean(ClickHouseJdbcHealthIndicator.class);

            final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
            assertEquals(HealthStatus.UP, result.getStatus());
            assertEquals("clickhouse-jdbc", result.getName());
        }
    }

    @Test
    void checkJdbcHealthDown() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.jdbc.url", String.format("jdbc:clickhouse://%s:%s/%s",
                container.getHost(), 12345, "default"));

        try (final ApplicationContext context = ApplicationContext.run(properties)) {
            final ClickHouseJdbcHealthIndicator indicator = context.getBean(ClickHouseJdbcHealthIndicator.class);

            final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
            assertEquals(HealthStatus.DOWN, result.getStatus());
            assertEquals("clickhouse-jdbc", result.getName());
        }
    }

    @Test
    void checkR2dbcHealthUp() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.r2dbc.url", String.format("r2dbc:clickhouse:http://%s:%s@%s:%s/%s",
                container.getUsername(), container.getPassword(), "localhost",
                container.getMappedPort(ClickHouseContainer.HTTP_PORT), "default"));

        try (final ApplicationContext context = ApplicationContext.run(properties)) {
            final ClickHouseR2dbcHealthIndicator indicator = context.getBean(ClickHouseR2dbcHealthIndicator.class);

            final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
            assertEquals(HealthStatus.UP, result.getStatus());
            assertEquals("clickhouse-r2dbc", result.getName());
        }
    }

    @Test
    void checkR2dbcHealthDown() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.r2dbc.url", String.format("r2dbc:clickhouse:http://%s:%s@%s:%s/%s",
                container.getUsername(), container.getPassword(), container.getHost(),
                12345, "default"));

        try (final ApplicationContext context = ApplicationContext.run(properties)) {
            final ClickHouseR2dbcHealthIndicator indicator = context.getBean(ClickHouseR2dbcHealthIndicator.class);

            final HealthResult result = Flux.from(indicator.getResult()).blockFirst(Duration.ofSeconds(60));
            assertEquals(HealthStatus.DOWN, result.getStatus());
            assertEquals("clickhouse-r2dbc", result.getName());
        }
    }
}
