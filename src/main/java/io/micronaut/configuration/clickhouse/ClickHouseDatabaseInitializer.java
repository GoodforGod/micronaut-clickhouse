package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import io.micronaut.runtime.exceptions.ApplicationStartupException;
import jakarta.inject.Inject;
import java.time.Duration;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * ClickHouse database initializer activated by
 * {@link ClickHouseConfiguration#isCreateDatabaseIfNotExist()}
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX + ".create-database-if-not-exist", value = "true", defaultValue = "false")
@Requires(beans = ClickHouseConfiguration.class)
@Context
public class ClickHouseDatabaseInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    @Inject
    protected void setupDatabase(ClickHouseConfiguration configuration) {
        final String database = configuration.getProperties().getDatabase();
        final Duration timeout = configuration.getCreateDatabaseTimeout();
        if (ClickHouseSettings.DEFAULT_DATABASE.equals(database)) {
            logger.debug("ClickHouse is configured to use 'default' Database, skipping initialization");
            return;
        }

        final ClickHouseProperties properties = new ClickHouseProperties(configuration.getProperties());
        properties.setDatabase(ClickHouseSettings.DEFAULT_DATABASE);
        properties.setConnectionTimeout(Math.toIntExact(timeout.toMillis()));
        properties.setDataTransferTimeout(Math.toIntExact(timeout.toMillis()));
        final ClickHouseConfiguration newConfiguration = new ClickHouseConfiguration(properties);

        logger.debug("ClickHouse Database '{}' initialization starting...", database);
        final long setupStart = System.nanoTime();
        try (ClickHouseConnection clickHouseConnection = new ClickHouseDriver().connect(newConfiguration.getUrl(), properties)) {
            try (ClickHouseStatement statement = clickHouseConnection.createStatement()) {
                statement.execute("CREATE DATABASE IF NOT EXISTS " + database);
            }
        } catch (Exception e) {
            throw new ApplicationStartupException("ClickHouse Database initialization failed due to: " + e.getMessage(), e);
        }

        final long tookNanoTime = System.nanoTime() - setupStart;
        logger.info("ClickHouse Database '{}' initialization took '{}' millis", database, tookNanoTime / 1000000);
    }
}
