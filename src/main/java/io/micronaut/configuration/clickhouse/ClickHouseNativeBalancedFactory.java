package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.BalancedClickhouseDataSource;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.3.2021
 */
@Requires(beans = ClickHouseNativeConfiguration.class)
@Requires(classes = com.github.housepower.jdbc.BalancedClickhouseDataSource.class)
@Factory
public class ClickHouseNativeBalancedFactory {

    @Named("clickhouse-native")
    @Bean
    @Singleton
    public com.github.housepower.jdbc.BalancedClickhouseDataSource getConnection(ClickHouseNativeConfiguration configuration) {
        final String jdbcUrl = configuration.getUrl();
        // due bug that charset is not matched by url template
        final String fixedJdbc = jdbcUrl.replace("&charset=" + configuration.getConfig().charset(), "");
        return new BalancedClickhouseDataSource(fixedJdbc);
    }
}
