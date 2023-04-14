package io.micronaut.configuration.clickhouse.health;

import static io.micronaut.health.HealthStatus.DOWN;
import static io.micronaut.health.HealthStatus.UP;

import com.clickhouse.r2dbc.connection.ClickHouseConnectionFactory;
import io.micronaut.configuration.clickhouse.ClickHouseR2dbcConnectionFactory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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
@Requires(property = "endpoints.health.clickhouse.r2dbc.enabled", value = "true", defaultValue = "true")
@Requires(classes = ClickHouseConnectionFactory.class)
@Requires(beans = ClickHouseR2dbcConnectionFactory.class)
@Singleton
public class ClickHouseR2dbcHealthIndicator implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConnectionFactory connectionFactory;
    private final ClickHouseHealthConfiguration healthConfiguration;

    @Inject
    public ClickHouseR2dbcHealthIndicator(ConnectionFactory connectionFactory,
                                          ClickHouseHealthConfiguration healthConfiguration) {
        this.connectionFactory = connectionFactory;
        this.healthConfiguration = healthConfiguration;
    }

    @Override
    public Publisher<HealthResult> getResult() {
        return Mono.usingWhen(Mono.fromDirect(connectionFactory.create()),
                connection -> Mono.fromDirect(connection.createStatement("SELECT 1").execute())
                        .flatMapMany(result -> result.map(this::buildUpReport))
                        .next(),
                Connection::close, (o, throwable) -> o.close(), Connection::close)
                .timeout(healthConfiguration.getR2dbc().getTimeout())
                .retry(healthConfiguration.getR2dbc().getRetry())
                .onErrorResume(e -> Mono.just(buildDownReport(e)));
    }

    private HealthResult buildUpReport(Row row, RowMetadata metadata) {
        logger.debug("Health '{}' reported UP with details", ClickHouseHealthConfiguration.NAME);
        return getBuilder()
                .status(UP)
                .build();
    }

    private HealthResult buildDownReport(Throwable e) {
        logger.warn("Health '{}' reported DOWN with error: {}", ClickHouseHealthConfiguration.NAME, e.getMessage());
        return getBuilder()
                .status(DOWN)
                .exception(e)
                .build();
    }

    private static HealthResult.Builder getBuilder() {
        return HealthResult.builder(ClickHouseHealthConfiguration.NAME);
    }
}
