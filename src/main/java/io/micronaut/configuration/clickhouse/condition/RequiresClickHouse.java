package io.micronaut.configuration.clickhouse.condition;

import io.micronaut.configuration.clickhouse.ClickHouseSettings;
import io.micronaut.context.annotation.Requires;
import java.lang.annotation.*;

/**
 * A custom requirement for ClickHouse.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Requires(property = ClickHouseSettings.PREFIX)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
public @interface RequiresClickHouse {}
