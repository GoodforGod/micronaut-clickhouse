package io.micronaut.configuration.clickhouse;

import io.micronaut.core.util.StringUtils;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * Abstract ClickHouse configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 2.4.2020
 */
public abstract class AbstractClickHouseConfiguration {

    public String getURL(ClickHouseProperties properties) {
        return getURL(properties.getHost(), properties.getPort(), properties.getSsl());
    }

    public String getURL(String host, int port, boolean isSsl) {
        return (isSsl)
                ? String.format("https://%s:%s", host, port)
                : String.format("http://%s:%s", host, port);
    }

    public String getJDBC(ClickHouseProperties properties) {
        return getJDBC(properties.getHost(), properties.getPort(), properties.getDatabase());
    }

    public String getJDBC(String host, int port, String database) {
        if (StringUtils.isEmpty(host))
            throw new IllegalArgumentException("ClickHouse Host is empty!");

        if (StringUtils.isEmpty(database))
            throw new IllegalArgumentException("ClickHouse Database is empty!");

        return String.format("%s//%s:%s/%s", ClickhouseJdbcUrlParser.JDBC_CLICKHOUSE_PREFIX, host, port, database);
    }
}
