package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickHouseSettings;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import java.time.Duration;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.08.2021
 */
@ConfigurationProperties("endpoints.health.clickhouse")
public class ClickHouseHealthConfiguration {

    /**
     * The name to expose details with.
     */
    public static final String NAME = "clickhouse";

    public static class Enabled {

        private Duration timeout = Duration.ofSeconds(10);
        private int retry = 2;
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            if (timeout == null)
                return;
            if (timeout.isNegative())
                throw new ConfigurationException("Timeout for health can not be less than 0");
            this.timeout = timeout;
        }

        public int getRetry() {
            return retry;
        }

        public void setRetry(int retry) {
            if (retry < 1)
                throw new ConfigurationException("Retry for health can not be less than 1");
            this.retry = retry;
        }

        @Override
        public String toString() {
            return "[timeout=" + timeout + ", retry=" + retry + ", enabled=" + enabled + ']';
        }
    }

    private boolean enabled = true;

    @ConfigurationBuilder("jdbc")
    private final Enabled jdbc = new Enabled();
    @ConfigurationBuilder("r2dbc")
    private final Enabled r2dbc = new Enabled();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Enabled getJdbc() {
        return jdbc;
    }

    public Enabled getR2dbc() {
        return r2dbc;
    }

    @Override
    public String toString() {
        return "[enabled=" + enabled + ", jdbc=" + jdbc + ", r2dbc=" + r2dbc + ']';
    }
}
