package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickHouseConfiguration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.inject.Singleton;
import java.util.Collections;
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
@Requires(property = "clickhouse.health.enabled", value = "true", defaultValue = "true")
@Requires(beans = ClickHouseConfiguration.class)
@Singleton
public class ClickHouseHealthIndicator implements HealthIndicator {

    /**
     * The name to expose details with.
     */
    private static final String NAME = "clickhouse";
    private final String uri;
    private final HttpClient client;
    private final String database;

    public ClickHouseHealthIndicator(ClickHouseConfiguration configuration, HttpClient client) {
        this.client = client;
        this.database = configuration.getProperties().getDatabase();
        this.uri = buildClickHouseURI(configuration);
    }

    @Override
    public Publisher<HealthResult> getResult() {
        return Flowable.fromPublisher(client.retrieve(uri))
                .map(this::buildUpReport)
                .timeout(10, SECONDS)
                .retry(3)
                .onErrorReturn(this::buildDownReport);
    }

    private HealthResult buildUpReport(String response) {
        final Map<String, String> details = Collections.singletonMap("database", database);
        return getBuilder().details(details).status(UP).build();
    }

    private HealthResult buildDownReport(Throwable t) {
        if (t instanceof HttpClientResponseException
                && HttpStatus.INTERNAL_SERVER_ERROR.equals(((HttpClientResponseException) t).getStatus())) {
            final String errorMessage = String.format("ClickHouse responded with '500' code and message: %s",
                    ((HttpClientResponseException) t).getResponse());
            return getBuilder().status(DOWN).details(errorMessage).build();
        }

        return getBuilder().status(DOWN).exception(t).build();
    }

    private static HealthResult.Builder getBuilder() {
        return HealthResult.builder(NAME);
    }

    private static String buildClickHouseURI(ClickHouseConfiguration configuration) {
        final ClickHouseProperties properties = configuration.getProperties();
        return String.format("%s:%s/ping", properties.getHost(), properties.getPort());
    }
}
