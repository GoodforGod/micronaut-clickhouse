package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.BalancedClickhouseDataSource;
import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.3.2021
 */
@Testcontainers
class ClickHouseBalancedTests extends ClickhouseRunner {

    @Container
    private final ClickHouseContainer container = getContainer();

    @Container
    private final ClickHouseContainer container2 = getContainer();

    @Test
    void getBalancedConnection() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.native.port", container.getMappedPort(ClickHouseContainer.NATIVE_PORT));

        final ApplicationContext context = ApplicationContext.run(properties);
        final BalancedClickhouseDataSource source = context.getBean(BalancedClickhouseDataSource.class);
        final ru.yandex.clickhouse.BalancedClickhouseDataSource sourceOfficial = context
                .getBean(ru.yandex.clickhouse.BalancedClickhouseDataSource.class);

        assertTrue(source.getConnection().createStatement().execute(container.getTestQueryString()));
        assertTrue(sourceOfficial.getConnection().createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBalancedConnectionForJdbcUrlWithMultipleHosts() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        final String officialJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.HTTP_PORT)
                + ",localhost:" + container2.getMappedPort(ClickHouseContainer.HTTP_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.url", officialJdbcUrl);

        final String nativeJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.NATIVE_PORT)
                + ",localhost:" + container2.getMappedPort(ClickHouseContainer.NATIVE_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.native.url", nativeJdbcUrl);

        final ApplicationContext context = ApplicationContext.run(properties);
        final BalancedClickhouseDataSource source = context.getBean(BalancedClickhouseDataSource.class);
        final ru.yandex.clickhouse.BalancedClickhouseDataSource sourceOfficial = context
                .getBean(ru.yandex.clickhouse.BalancedClickhouseDataSource.class);

        assertTrue(source.getConnection().createStatement().execute(container.getTestQueryString()));
        assertTrue(sourceOfficial.getConnection().createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBalancedConnectionForJdbcUrl() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        final String officialJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.HTTP_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.url", officialJdbcUrl);

        final String nativeJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.NATIVE_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.native.url", nativeJdbcUrl);

        final ApplicationContext context = ApplicationContext.run(properties);
        final BalancedClickhouseDataSource source = context.getBean(BalancedClickhouseDataSource.class);
        final ru.yandex.clickhouse.BalancedClickhouseDataSource sourceOfficial = context
                .getBean(ru.yandex.clickhouse.BalancedClickhouseDataSource.class);

        assertTrue(source.getConnection().createStatement().execute(container.getTestQueryString()));
        assertTrue(sourceOfficial.getConnection().createStatement().execute(container.getTestQueryString()));
    }

    @Test
    void getBalancedConnectionForJdbcUrlNotRaw() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        final String officialJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.HTTP_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.url", officialJdbcUrl);
        properties.put("clickhouse.use-raw-url", false);

        final String nativeJdbcUrl = "jdbc:clickhouse://localhost:"
                + container.getMappedPort(ClickHouseContainer.NATIVE_PORT)
                + "/default?compress=1&decompress=2";
        properties.put("clickhouse.native.url", nativeJdbcUrl);
        properties.put("clickhouse.native.use-raw-url", false);

        final ApplicationContext context = ApplicationContext.run(properties);
        final BalancedClickhouseDataSource source = context.getBean(BalancedClickhouseDataSource.class);
        final ru.yandex.clickhouse.BalancedClickhouseDataSource sourceOfficial = context
                .getBean(ru.yandex.clickhouse.BalancedClickhouseDataSource.class);

        assertTrue(source.getConnection().createStatement().execute(container.getTestQueryString()));
        assertTrue(sourceOfficial.getConnection().createStatement().execute(container.getTestQueryString()));
    }
}
