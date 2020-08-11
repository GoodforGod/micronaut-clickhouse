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
@Requires(beans = ClickHouseConfiguration.class)
@Context
@Internal
public class ClickHouseDatabaseInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${arangodb.createDatabaseIfNotExist.timeout:10}")
    private Integer createTimeout;

    @PostConstruct
    @Inject
    protected void setupDatabase(ClickHouseConfiguration configuration) {
        if (!configuration.isCreateDatabaseIfNotExist()) {
            logger.debug("ClickHouse Database creation is set to 'false'");
            return;
        }

        final String database = configuration.getProperties().getDatabase();
        if (ClickHouseSettings.DEFAULT_DATABASE.equals(database)) {
            logger.debug("ClickHouse is configured to use 'default' Database");
            return;
        }

        final ClickHouseProperties properties = new ClickHouseProperties(configuration.getProperties());
        properties.setDatabase(ClickHouseSettings.DEFAULT_DATABASE);
        properties.setConnectionTimeout(createTimeout);
        properties.setDataTransferTimeout(createTimeout);
        final ClickHouseConfiguration newConfiguration = new ClickHouseConfiguration(properties);

        final long setupStart = System.nanoTime();
        try (ClickHouseConnection clickHouseConnection = new ClickHouseDriver().connect(newConfiguration.getJDBC(), properties)) {
            try (ClickHouseStatement statement = clickHouseConnection.createStatement()) {
                statement.execute("CREATE DATABASE IF NOT EXISTS " + database);
            }
        } catch (RuntimeException e) {
            throw new ConfigurationException("ClickHouse Database creation failed due to: " + e.getCause().getMessage());
        } catch (Exception e) {
            throw new ConfigurationException("ClickHouse Database creation failed due to: " + e.getMessage());
        }

        final long tookNanoTime = System.nanoTime() - setupStart;
        logger.info("ClickHouse Database '{}' creation took '{}' millis", database, tookNanoTime / 1000000);
    }
}
