package io.micronaut.configuration.clickhouse.health;

import io.micronaut.configuration.clickhouse.ClickHouseSettings;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;

/**
 * Please Add Description Here.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.08.2021
 */
@Requires(property = ClickHouseSettings.PREFIX)
@ConfigurationProperties(ClickHouseSettings.PREFIX + ".health")
public class ClickHouseHealthConfiguration {

    private boolean enabled = true;
    private long timeoutInMillis = 10000;
    private int retry = 2;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getTimeoutInMillis() {
        return timeoutInMillis;
    }

    public void setTimeoutInMillis(long timeoutInMillis) {
        if (timeoutInMillis < 0)
            throw new ConfigurationException("Timeout for health can not be less than 0");
        this.timeoutInMillis = timeoutInMillis;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        if (retry < 1)
            throw new ConfigurationException("Retry for health can not be less than 1");
        this.retry = retry;
    }
}
