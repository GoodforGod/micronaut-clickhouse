package io.micronaut.configuration.clickhouse;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.micronaut.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class ClickHouseJdbcTests extends ClickhouseRunner {

    @Container
    private static final ClickHouseContainer container = getContainer();

    @Test
    void statementExecuted() throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.jdbc.url", container.getJdbcUrl());

        try (final ApplicationContext context = ApplicationContext.run(properties)) {
            final ClickHouseDataSource dataSource = context.getBean(ClickHouseDataSource.class);

            try (var connection = dataSource.getConnection()) {
                try (var statement = connection.createStatement()) {
                    assertTrue(statement.execute(container.getTestQueryString()));
                }
            }
        }
    }
}
