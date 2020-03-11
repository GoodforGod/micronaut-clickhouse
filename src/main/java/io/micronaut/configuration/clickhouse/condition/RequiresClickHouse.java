package io.micronaut.configuration.clickhouse.condition;

import java.lang.annotation.*;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 11.3.2020
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
public @interface RequiresClickHouse {
}
