package io.micronaut.configuration.clickhouse;

/**
 * Common constants to for ClickHouse settings.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
public interface ClickHouseSettings {

    /**
     * Prefix to use for all ClickHouse settings.
     */
    String PREFIX = "clickhouse";

    /**
     * Prefix to use for Native Driver ClickHouse settings.
     */
    String PREFIX_NATIVE = PREFIX + ".native";

    /**
     * ClickHouse default HOST
     */
    String DEFAULT_HOST = "127.0.0.1";

    /**
     * ClickHouse default http connection PORT
     */
    int DEFAULT_PORT = 8123;

    /**
     * ClickHouse default native connection PORT
     */
    int DEFAULT_NATIVE_PORT = 9000;

    /**
     * ClickHouse default DATABASE
     */
    String DEFAULT_DATABASE = "default";
}
