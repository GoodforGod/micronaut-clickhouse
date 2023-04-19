# Micronaut ClickHouse Configuration

[![Minimum required Java version](https://img.shields.io/badge/Java-11%2B-blue?logo=openjdk)](https://openjdk.org/projects/jdk/11/)
![Java CI](https://github.com/GoodforGod/micronaut-clickhouse/workflows/Java%20CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)

This project includes integration between Micronaut and ClickHouse, autocompletion for configuration, health check.

## Dependency :rocket:

**Gradle**
```groovy
implementation "com.github.goodforgod:micronaut-clickhouse:5.0.0"
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>micronaut-clickhouse</artifactId>
    <version>5.0.0</version>
</dependency>
```

## JDBC

Official JDBC driver provides *DataSource*:
```java
@Inject
private ClickHouseDataSource clickHouseDataSource;

@Named("clickhouse")
@Inject
private DataSource dataSource;
```

### Configuration

Official Configuration supports all available ClickHouse driver settings.

Check [ClickHouse Official settings file](https://github.com/ClickHouse/clickhouse-java/blob/main/clickhouse-client/src/main/java/com/clickhouse/client/config/ClickHouseDefaults.java)
for info about all parameters.
```yaml
clickhouse:
  jdbc:
    url: jdbc:clickhouse://localhost:8529/default?compress=1
    use-options: true             # default - true
    options:
      user: default
      password: default
    use-custom-options: true      # default - true
    custom-options:
      my-option: 1
```

## R2DBC

Official R2DBC driver provides *ConnectionFactory*:
```java
@Named("clickhouse")
@Inject
private ConnectionFactory connectionFactory;
```

### Configuration

R2DBC configuration example:
```yaml
clickhouse:
  r2dbc:
    url: r2dbc:clickhouse:http://localhost:8529/default?compress=1
```

## Health Check

Health check for ClickHouse is provided for *JDBC* & *R2DBC* and is *turned on* by default.

Micronaut health check is part of [Micronaut Health Endpoint](https://docs.micronaut.io/latest/guide/index.html#healthEndpoint).

Example of ClickHouse health:
```json
{
  "name": "service",
  "status": "UP",
  "details": {
    "clickhouse-jdbc": {
      "name": "clickhouse",
      "status": "UP"
    },
    "clickhouse-r2dbc": {
      "name": "clickhouse",
      "status": "UP"
    }
  }
}
```

Where *database* name service is connected same as [configuration says](#Configuration).

You can explicitly *turn off* health check or configure it.

```yaml
endpoints:
  health:
   clickhouse:
     enabled: true               # default - true 
     jdbc:
       enabled: true             # default - true 
       timeout: 10000ms          # default - 10000ms
       retry: 2                  # default - 2
     r2dbc:
       enabled: true             # default - true 
       timeout: 10000ms          # default - 10000ms
       retry: 2                  # default - 2
```

## Testing

For testing purposes you can use [ClickHouse TestContainer library](https://www.testcontainers.org/modules/databases/clickhouse/).

TestContainers allows you to use integration tests against real database in all docker friendly environments, 
check here for [TestContainers](https://www.testcontainers.org/).

## Micronaut Compatability

Starting from version *3.0.0* library ships for *Micronaut 3*.

Starting from version *2.1.0* Java 11+ is required (previous version 1.8+ compatible).

Starting from version *2.0.0* library ships for *Micronaut 2*.

Last release for **Micronaut 1** is [version *1.0.2*](https://github.com/GoodforGod/micronaut-clickhouse/releases/tag/v1.0.2).

## License

This project licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
