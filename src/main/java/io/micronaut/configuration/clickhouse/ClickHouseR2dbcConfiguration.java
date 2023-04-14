package io.micronaut.configuration.clickhouse;

import com.clickhouse.jdbc.ClickHouseDriver;
import com.clickhouse.r2dbc.connection.ClickHouseConnectionFactory;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;

import java.util.Collections;
import java.util.Map;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseConnectionFactory.class)
@Requires(property = ClickHouseSettings.PREFIX + ".r2dbc")
@ConfigurationProperties(ClickHouseSettings.PREFIX + ".r2dbc")
public class ClickHouseR2dbcConfiguration {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
