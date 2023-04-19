package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import io.r2dbc.spi.ConnectionFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class ClickHouseR2dbcTests extends ClickhouseRunner {

    @Container
    private final ClickHouseContainer container = getContainer();

    @Test
    void statementExecuted() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.r2dbc.url", String.format("r2dbc:clickhouse:http://%s:%s@%s:%s/%s",
                container.getUsername(), container.getPassword(), container.getHost(),
                container.getMappedPort(ClickHouseContainer.HTTP_PORT), "default"));

        final ApplicationContext context = ApplicationContext.run(properties);
        final ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);

        final Integer result = Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(connection.createStatement("SELECT 1").execute()))
                .flatMap(res -> Mono.from(res.map(readable -> readable.get(0, Integer.class))))
                .block(Duration.ofMinutes(1));
        assertEquals(1, result);
    }
}
