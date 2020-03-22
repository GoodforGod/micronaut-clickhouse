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
     * ClickHouse default HOST
     */
    String DEFAULT_HOST = "localhost";

    /**
     * ClickHouse default PORT
     */
    int DEFAULT_PORT = 8123;

    /**
     * ClickHouse default DATABASE
     */
    String DEFAULT_DATABASE = "default";
}
