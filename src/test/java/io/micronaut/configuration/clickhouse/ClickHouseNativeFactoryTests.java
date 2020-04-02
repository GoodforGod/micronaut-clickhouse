package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
    void nativeConnectionTestQuerySuccess() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.native.port", container.getFirstMappedPort() - 1);

        final ApplicationContext context = ApplicationContext.run(properties);
        final com.github.housepower.jdbc.ClickHouseConnection connection = context
                .getBean(com.github.housepower.jdbc.ClickHouseConnection.class);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBothOfficialAndNativeConnectionBeans() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getFirstMappedPort());
        properties.put("clickhouse.native.port", container.getFirstMappedPort() - 1);

        final ApplicationContext context = ApplicationContext.run(properties);
        final com.github.housepower.jdbc.ClickHouseConnection connectionNative = context
                .getBean(com.github.housepower.jdbc.ClickHouseConnection.class);
        final ru.yandex.clickhouse.ClickHouseConnection connectionOfficial = context
                .getBean(ru.yandex.clickhouse.ClickHouseConnection.class);

        assertTrue(connectionOfficial.createStatement().execute(container.getTestQueryString()));
        assertTrue(connectionNative.createStatement().execute(container.getTestQueryString()));
    }
}
