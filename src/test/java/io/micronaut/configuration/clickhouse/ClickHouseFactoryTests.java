package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.ClickHouseConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseFactoryTests extends ClickhouseRunner {

    @Container
    private final ClickHouseContainer container = getContainer();

    @Test
    void officialConnectionTestQuerySuccess() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConnection connection = context.getBean(ClickHouseConnection.class);

        final String version = connection.getServerVersion();
        assertEquals(getClickhouseVersion(), version);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBothOfficialAndNativeConnectionBeans() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.native.port", container.getMappedPort(ClickHouseContainer.NATIVE_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final com.github.housepower.jdbc.ClickHouseConnection connectionNative = context
                .getBean(com.github.housepower.jdbc.ClickHouseConnection.class);
        final ClickHouseConnection connectionOfficial = context
                .getBean(ClickHouseConnection.class);

        assertTrue(connectionOfficial.createStatement().execute(container.getTestQueryString()));
        assertTrue(connectionNative.createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBalancedConnection() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.native.port", container.getMappedPort(ClickHouseContainer.NATIVE_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final BalancedClickhouseDataSource sourceOfficial = context.getBean(BalancedClickhouseDataSource.class);

        assertTrue(sourceOfficial.getConnection().createStatement().execute(container.getTestQueryString()));
    }
}
