package io.micronaut.configuration.clickhouse;

/**
 * Common constants to for ClickHouse settings.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
public final class ClickHouseSettings {

    private ClickHouseSettings() {}

    /**
     * Prefix to use for all ClickHouse settings.
     */
    public static final String PREFIX = "clickhouse";

    /**
     * Prefix to use for Native Driver ClickHouse settings.
     */
    public static final String PREFIX_NATIVE = PREFIX + ".native";

    /**
     * ClickHouse default HOST
     */
    public static final String DEFAULT_HOST = "127.0.0.1";

    /**
     * ClickHouse default http connection PORT
     */
    public static final int DEFAULT_PORT = 8123;

    /**
     * ClickHouse default native connection PORT
     */
    public static final int DEFAULT_NATIVE_PORT = 9000;

    /**
     * ClickHouse default DATABASE
     */
    public static final String DEFAULT_DATABASE = "default";
}
