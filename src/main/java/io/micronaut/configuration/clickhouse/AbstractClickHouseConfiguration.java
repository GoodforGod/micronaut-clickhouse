package io.micronaut.configuration.clickhouse;

import static ru.yandex.clickhouse.ClickhouseJdbcUrlParser.JDBC_CLICKHOUSE_PREFIX;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import ru.yandex.clickhouse.ClickhouseJdbcUrlParser;

/**
 * Abstract ClickHouse configuration class.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 2.4.2020
 */
public abstract class AbstractClickHouseConfiguration {

    protected static final Pattern URL_TEMPLATE = Pattern.compile(JDBC_CLICKHOUSE_PREFIX + "" +
            "//([a-zA-Z0-9_:,.-]+)" +
            "(/[a-zA-Z0-9_]+" +
            "([?][a-zA-Z0-9_]+[=][a-zA-Z0-9_]+([&][a-zA-Z0-9_]+[=][a-zA-Z0-9_]+)*)?" +
            ")?");

    protected static List<String> splitUrl(final String url) {
        final Matcher m = URL_TEMPLATE.matcher(url);
        if (!m.matches())
            throw new IllegalArgumentException("Incorrect url: " + url);

        final String database = (m.group(2) == null)
                ? ClickHouseSettings.DEFAULT_DATABASE
                : m.group(2);

        return Arrays.stream(m.group(1).split(","))
                .map(host -> JDBC_CLICKHOUSE_PREFIX + "//" + host + database)
                .collect(Collectors.toList());
    }

    protected String getJdbcUrl(String host, int port, String database, Properties properties) {
        return ClickhouseJdbcUrlParser.JDBC_CLICKHOUSE_PREFIX + "//" + host + ":" + port + "/" + database
                + getJdbcProperties(properties);
    }

    protected String getJdbcProperties(Properties properties) {
        final StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = String.valueOf(entry.getKey());
            final String value = String.valueOf(entry.getValue());
            final String delimiter = (isFirst) ? "?" : "&";
            if (key.isBlank() || value.isBlank()
                    || key.contains("/") || value.contains("/")
                    || key.contains("-") || value.contains("-"))
                continue;

            builder.append(delimiter).append(entry.getKey()).append("=").append(entry.getValue());
            isFirst = false;
        }

        return builder.toString();
    }
}
