package io.micronaut.configuration.clickhouse;

/**
 * Configuration for enabled properties
 *
 * @author Anton Kurako (GoodforGod)
 * @since 23.10.2020
 */
public class EnableConfiguration {

    private boolean enabled;

    public EnableConfiguration(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
