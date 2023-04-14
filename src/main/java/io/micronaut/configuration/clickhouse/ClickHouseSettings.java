package io.micronaut.configuration.clickhouse;

/**
 * Common constants to for ClickHouse settings.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
public final class ClickHouseSettings {

    private ClickHouseSettings() {}

    public static final String QUALIFIER = "clickhouse";

    /**
     * Prefix to use for all ClickHouse settings.
     */
    public static final String PREFIX = "clickhouse";

    /**
     * ClickHouse default DATABASE
     */
    public static final String DEFAULT_DATABASE = "default";
}
