package io.micronaut.configuration.clickhouse;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.util.Properties;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
//@Requires(property = ClickHouseSettings.PREFIX)
//@ConfigurationProperties(ClickHouseSettings.PREFIX)
public class ClickHouseNativeConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @ConfigurationBuilder(prefixes = "set")
    private Properties properties;

    private boolean createDatabaseIfNotExist = false;

}
