package io.micronaut.configuration.clickhouse;

import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 20.3.2021
 */
public abstract class ClickhouseRunner extends Assertions {

    protected static String getClickhouseVersion() {
        return "21.3.20.1";
    }

    protected ClickHouseContainer getContainer() {
        return new ClickHouseContainer(DockerImageName.parse("yandex/clickhouse-server:" + getClickhouseVersion()));
    }
}
