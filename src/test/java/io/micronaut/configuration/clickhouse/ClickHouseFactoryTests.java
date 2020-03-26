package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.clickhouse.ClickHouseConnection;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseFactoryTests extends Assertions {

    @Container
    private final ClickHouseContainer container = new ClickHouseContainer();

    @Test
    void defaultClientConnectsToDatabase() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getFirstMappedPort());

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConnection connection = context.getBean(ClickHouseConnection.class);

        final String version = connection.getServerVersion();
        assertEquals("18.10.3", version);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));
    }
}
