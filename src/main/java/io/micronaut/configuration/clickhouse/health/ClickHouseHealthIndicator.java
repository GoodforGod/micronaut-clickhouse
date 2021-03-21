package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickHouseConfiguration;
import io.micronaut.configuration.clickhouse.ClickHouseSettings;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static io.micronaut.health.HealthStatus.DOWN;
import static io.micronaut.health.HealthStatus.UP;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A {@link HealthIndicator} for ClickHouse.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX + ".health.enabled", value = "true", defaultValue = "true")
@Requires(beans = ClickHouseConfiguration.class)
@Singleton
public class ClickHouseHealthIndicator implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The name to expose details with.
     */
    private static final String NAME = "clickhouse";
    private final HttpClient client;
    private final String database;

    public ClickHouseHealthIndicator(ClickHouseConfiguration configuration) {
        try {
            this.client = RxHttpClient.create(new URL(configuration.getURL()));
            this.database = configuration.getProperties().getDatabase();
        } catch (MalformedURLException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    @Override
    public Publisher<HealthResult> getResult() {
        return Flowable.fromPublisher(client.retrieve("/ping"))
                .map(this::buildUpReport)
                .timeout(5, SECONDS)
                .retry(3)
                .onErrorReturn(this::buildDownReport);
    }

    private HealthResult buildUpReport(String response) {
        final Map<String, String> details = Map.of("database", database);
        logger.debug("Health '{}' reported UP with details: {}", NAME, details);
        return getBuilder()
                .details(details)
                .status(UP)
                .build();
    }

    private HealthResult buildDownReport(Throwable e) {
        logger.debug("Health '{}' reported DOWN with error: {}", NAME, e.getMessage());
        return getBuilder()
                .status(DOWN)
                .exception(e)
                .build();
    }

    private static HealthResult.Builder getBuilder() {
        return HealthResult.builder(NAME);
    }
}
