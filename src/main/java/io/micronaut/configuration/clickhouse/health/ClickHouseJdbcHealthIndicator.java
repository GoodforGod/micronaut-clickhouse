package io.micronaut.configuration.clickhouse.health;

import static io.micronaut.health.HealthStatus.DOWN;
import static io.micronaut.health.HealthStatus.UP;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;
import io.micronaut.configuration.clickhouse.ClickHouseJdbcDataSourceFactory;
import io.micronaut.configuration.clickhouse.ClickHouseSettings;
import io.micronaut.context.annotation.Requires;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * A {@link HealthIndicator} for ClickHouse.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(property = "endpoints.health.clickhouse.enabled", value = "true", defaultValue = "true")
@Requires(property = "endpoints.health.clickhouse.jdbc.enabled", value = "true", defaultValue = "true")
@Requires(classes = ClickHouseDataSource.class)
@Requires(beans = ClickHouseJdbcDataSourceFactory.class)
@Singleton
public class ClickHouseJdbcHealthIndicator implements HealthIndicator {

    private static final String NAME = "clickhouse-jdbc";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ClickHouseDataSource clickHouseDataSource;
    private final ClickHouseHealthConfiguration healthConfiguration;

    @Inject
    public ClickHouseJdbcHealthIndicator(@Named(ClickHouseSettings.QUALIFIER) ClickHouseDataSource clickHouseDataSource,
                                         ClickHouseHealthConfiguration healthConfiguration) {
        this.clickHouseDataSource = clickHouseDataSource;
        this.healthConfiguration = healthConfiguration;
    }

    @Override
    public Publisher<HealthResult> getResult() {
        return Mono.<String>create(sink -> {
            try (ClickHouseConnection connection = clickHouseDataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT 1")) {
                    statement.execute();
                    sink.success(connection.getCurrentDatabase());
                }
            } catch (SQLException e) {
                sink.error(e);
            }
        })
                .map(r -> buildUpReport())
                .timeout(healthConfiguration.getJdbc().getTimeout())
                .retry(healthConfiguration.getJdbc().getRetry())
                .onErrorResume(e -> Mono.just(buildDownReport(e)));
    }

    private HealthResult buildUpReport() {
        logger.debug("Health '{}' reported UP", NAME);
        return getBuilder()
                .status(UP)
                .build();
    }

    private HealthResult buildDownReport(Throwable e) {
        logger.warn("Health '{}' reported DOWN with error: {}", NAME, e.getMessage());
        return getBuilder()
                .status(DOWN)
                .exception(e)
                .build();
    }

    private static HealthResult.Builder getBuilder() {
        return HealthResult.builder(NAME);
    }
}
