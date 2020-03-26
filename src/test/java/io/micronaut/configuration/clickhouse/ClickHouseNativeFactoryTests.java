package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.ClickHouseConnection;
import io.micronaut.context.ApplicationContext;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
class ClickHouseNativeFactoryTests extends Assertions {

    @Container
    private final ClickHouseContainer container = new ClickHouseContainer();

    @Test
    void defaultClientConnectsToDatabase() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getFirstMappedPort());

        final ApplicationContext context = ApplicationContext.run(properties);
        final ru.yandex.clickhouse.ClickHouseConnection connection = context.getBean(ru.yandex.clickhouse.ClickHouseConnection.class);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));
    }
}
