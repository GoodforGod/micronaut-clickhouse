package io.micronaut.configuration.clickhouse;

import com.clickhouse.r2dbc.connection.ClickHouseConnectionFactory;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

/**
 * ClickHouse Official R2DBC Driver configuration
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
