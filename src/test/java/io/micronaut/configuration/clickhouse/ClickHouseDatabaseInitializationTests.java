package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.exceptions.ConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.clickhouse.ClickHouseConnection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@Testcontainers
class ClickHouseDatabaseInitializationTests extends Assertions {

    @Container
    private final ClickHouseContainer container = new ClickHouseContainer();

    @Test
    void databaseInitializedWhenContextCreated() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.database", "custom");
        properties.put("clickhouse.createDatabaseIfNotExist", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConnection connection = context.getBean(ClickHouseConnection.class);

        final String version = connection.getServerVersion();
        assertEquals("18.10.3", version);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));

        connection.createStatement().execute("CREATE TABLE custom.example(" +
                " name String," +
                " registered DateTime " +
                ") ENGINE = MergeTree() " +
                " ORDER BY registered;");

        assertTrue(connection.createStatement().execute("SELECT * FROM custom.example"));
    }

    @Test
    void databaseDefaultInitializeSkip() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.database", ClickHouseSettings.DEFAULT_DATABASE);
        properties.put("clickhouse.createDatabaseIfNotExist", true);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConnection connection = context.getBean(ClickHouseConnection.class);

        final String version = connection.getServerVersion();
        assertEquals("18.10.3", version);

        assertTrue(connection.createStatement().execute(container.getTestQueryString()));

        connection.createStatement().execute("CREATE TABLE default.example(" +
                " name String," +
                " registered DateTime " +
                ") ENGINE = MergeTree() " +
                " ORDER BY registered;");

        assertTrue(connection.createStatement().execute("SELECT * FROM default.example"));
    }

    @Test
    void databaseCreationIsOff() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", container.getMappedPort(ClickHouseContainer.HTTP_PORT));
        properties.put("clickhouse.database", ClickHouseSettings.DEFAULT_DATABASE);
        properties.put("clickhouse.createDatabaseIfNotExist", false);

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseConnection connection = context.getBean(ClickHouseConnection.class);

        try {
            connection.createStatement().execute(container.getTestQueryString());
        } catch (Exception e) {
            assertTrue(e.getCause().getCause() instanceof SQLException);
            assertEquals(81, ((SQLException) e.getCause().getCause()).getErrorCode());
        }
    }

    @Test
    void startUpForContextFailsOnTimeout() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.port", 7457);
        properties.put("clickhouse.database", "customos");
        properties.put("clickhouse.createDatabaseIfNotExist", true);
        properties.put("clickhouse.createDatabaseIfNotExist.timeout", 1);

        try {
            ApplicationContext.run(properties);
            fail("Should not happen!");
        } catch (Exception e) {
            assertTrue(e.getCause().getCause() instanceof ConfigurationException);
            assertTrue(e.getCause().getCause().getMessage().startsWith("ClickHouse Database creation failed"));
        }
    }
}
