package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.config.ClickHouseDefaults;
import com.clickhouse.jdbc.ClickHouseDriver;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import java.util.Map;
import java.util.Properties;

/**
 * ClickHouse Official JDBC Driver configuration
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
    private boolean useOptions = true;
    private boolean useCustomOptions = true;

    @ConfigurationBuilder("options")
    private final ClickHouseJdbcOptions options = new ClickHouseJdbcOptions();

    /**
     * @see ClickHouseDefaults
     */
    @Property(name = "customOptions")
    private Map<String, Object> customOptions;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUseOptions() {
        return useOptions;
    }

    public void setUseOptions(boolean useOptions) {
        this.useOptions = useOptions;
    }

    public boolean isUseCustomOptions() {
        return useCustomOptions;
    }

    public void setUseCustomOptions(boolean useCustomOptions) {
        this.useCustomOptions = useCustomOptions;
    }

    public ClickHouseJdbcOptions getOptions() {
        return options;
    }

    public Properties getProperties() {
        final Properties props = new Properties();
        if (isUseCustomOptions() && customOptions != null) {
            props.putAll(customOptions);
        }

        if (isUseOptions()) {
            props.putAll(options.getAsMap());
        }

        return props;
    }

    public Map<String, Object> getCustomOptions() {
        return customOptions;
    }

    public void setCustomOptions(Map<String, Object> customOptions) {
        this.customOptions = customOptions;
    }

    @Override
    public String toString() {
        return "[url=" + url + ", properties=" + customOptions + ']';
    }
}
