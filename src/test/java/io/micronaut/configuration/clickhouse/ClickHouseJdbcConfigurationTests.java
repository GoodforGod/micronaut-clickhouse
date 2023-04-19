package io.micronaut.configuration.clickhouse;

import io.micronaut.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.3.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClickHouseJdbcConfigurationTests extends ClickhouseRunner {

    @Test
    void configApplied() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("clickhouse.jdbc.options", Map.of("user", "user1"));
        properties.put("clickhouse.jdbc.customOptions", Map.of("one", 1));

        final ApplicationContext context = ApplicationContext.run(properties);
        final ClickHouseJdbcConfiguration configuration = context.getBean(ClickHouseJdbcConfiguration.class);

        assertEquals("user1", configuration.getOptions().getUser());
        assertNotNull(configuration.getCustomOptions());
        assertEquals(1, configuration.getCustomOptions().get("one"));
    }
}
