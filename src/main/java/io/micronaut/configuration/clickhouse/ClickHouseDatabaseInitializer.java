package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.annotation.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * ClickHouse database initializer activated by
 * {@link ClickHouseConfiguration#isCreateDatabaseIfNotExist()}
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.3.2020
 */
@Requires(property = "clickhouse.createDatabaseIfNotExist", value = "true", defaultValue = "false")
@Context
@Internal
public class ClickHouseDatabaseInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${arangodb.createDatabaseIfNotExist.timeout:10}")
    private Integer createTimeout;

    @PostConstruct
    @Inject
    protected void setupDatabase(ClickHouseConfiguration configuration) {
        if (configuration.isCreateDatabaseIfNotExist()) {
            final String database = configuration.getProperties().getDatabase();
            if (ClickHouseSettings.DEFAULT_DATABASE.equals(database)) {
                logger.debug("Database initialization is turned of for 'default' database, skipping...");
                return;
            }

            final ClickHouseProperties properties = new ClickHouseProperties(configuration.getProperties());
            properties.setDatabase("default");
            properties.setConnectionTimeout(createTimeout);
            properties.setDataTransferTimeout(createTimeout);
            final ClickHouseConfiguration newConfiguration = new ClickHouseConfiguration(properties);

            final long setupStart = System.nanoTime();
            try (ClickHouseConnection clickHouseConnection = new ClickHouseDriver().connect(newConfiguration.getJDBC(), properties)) {
                try (ClickHouseStatement statement = clickHouseConnection.createStatement()) {
                    statement.execute("CREATE DATABASE IF NOT EXISTS " + database);
                }
            } catch (Exception e) {
                logger.error("Could not create '{}' database due to: {}", database, e.getMessage());
                throw new ConfigurationException("Could not initialize database due to connection failure: " + e.getMessage());
            }

            final long tookNanoTime = System.nanoTime() - setupStart;
            logger.info("Database '{}' initialization took '{}' millis", database, tookNanoTime / 1000000);
        } else {
            logger.debug("Database creation is turned off.");
        }
    }
}
