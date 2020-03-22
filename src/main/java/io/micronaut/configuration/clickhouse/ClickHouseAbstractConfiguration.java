package io.micronaut.configuration.clickhouse;

import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.3.2020
 */
abstract class ClickHouseAbstractConfiguration {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public String buildURL(String host, int port, String database) {
        if (StringUtils.isEmpty(host))
            throw new IllegalArgumentException("ClickHouse Host is empty!");

        if (StringUtils.isEmpty(database))
            throw new IllegalArgumentException("ClickHouse Database is empty!");

        final String url = String.format("%s//%s:%s/%s", ClickhouseJdbcUrlParser.JDBC_CLICKHOUSE_PREFIX, host, port, database);
        logger.debug("ClickHouse URL: {}", url);
        return url;
    }
}
