package io.micronaut.configuration.clickhouse;

import com.clickhouse.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @see com.clickhouse.client.config.ClickHouseClientOption
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
@Requires(classes = ClickHouseDriver.class)
@Requires(property = ClickHouseSettings.PREFIX + ".jdbc")
@ConfigurationProperties(ClickHouseSettings.PREFIX + ".jdbc")
public class ClickHouseJdbcConfiguration {

    private String url;

    @Property(name = "properties")
    private Map<String, Object> properties;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Properties getProperties() {
        if(properties == null) {
            return new Properties();
        } else {
            final Properties props = new Properties();
            props.putAll(properties);
            return props;
        }
    }

    @Override
    public String toString() {
        return "[url=" + url + ", properties=" + properties + ']';
    }
}
