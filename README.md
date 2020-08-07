# Micronaut ClickHouse Configuration

![Java CI](https://github.com/GoodforGod/micronaut-clickhouse/workflows/Java%20CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)

This project includes integration between Micronaut and ClickHouse.

## Dependency :rocket:

Starting from version *2.0.0* library ships for *Micronaut 2*.

Last release for **Micronaut 1** is [version *1.0.2*](https://github.com/GoodforGod/micronaut-clickhouse/releases/tag/v1.0.2).

**Gradle**
```groovy
dependencies {
    compile 'com.github.goodforgod:micronaut-clickhouse:2.0.0'
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>micronaut-clickhouse</artifactId>
    <version>2.0.0</version>
</dependency>
```


## Configuration

Includes a configuration to automatically configure official [ClickHouse Java drive](https://github.com/ClickHouse/clickhouse-jdbc)
or [ClickHouse Native Driver](https://github.com/housepower/ClickHouse-Native-JDBC). 
Just configure the host, port, credentials (if needed) of the ClickHouse driver in *application.yml*.

```yaml
clickhouse:
  host: 127.0.0.1       # default - 127.0.0.1
  port: 8529            # default - 8529
  database: default     # default - default
  native:
    port: 9000          # default - 9000
```

To use [official driver](https://github.com/ClickHouse/clickhouse-jdbc) just add a dependency to your application.

```groovy
compile 'ru.yandex.clickhouse:clickhouse-jdbc'
```

To use [native driver](https://github.com/housepower/ClickHouse-Native-JDBC) just add a dependency to your application.

```groovy
compile 'com.github.housepower:clickhouse-native-jdbc'
```

### Drivers

Both *ClickHouse Official* and *ClickHouse Native* connections are then available for dependency injection.

Connections are injected as [**singletons**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) 
beans remember that while using them.

```java
@Inject
private ru.yandex.clickhouse.ClickHouseConnection officialConnection;

@Inject
private com.github.housepower.jdbc.ClickHouseConnection nativeConnection;
```

In case you want to inject **[prototype](https://docs.micronaut.io/latest/guide/index.html#builtInScopes)**
connections, you can specify @Named *prototype* and connection prototype bean will be injected.

```java
@Named("prototype")
@Inject
private ru.yandex.clickhouse.ClickHouseConnection officialConnection;

@Named("prototype")
@Inject
private com.github.housepower.jdbc.ClickHouseConnection nativeConnection;
```

### Configuring ClickHouse Official Driver

Only *official connections* are provided as [**refreshable**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) with *ClickHouse* key for bean refresh.

Official Configuration supports all available ClickHouse driver settings.

Check [ClickHouse Official settings file](https://github.com/ClickHouse/clickhouse-jdbc/blob/master/src/main/java/ru/yandex/clickhouse/settings/ClickHouseProperties.java) 
for info about all parameters.
```yaml
clickhouse:
  async: true                           # default - false
  ssl: true                             # default - false
  maxRedirects: 5
  ...
```

### Configuring ClickHouse Native Driver


**Remember** that native driver uses **port different from official** driver, 
which is default to *9000* and not *8529*.
So your ClickHouse instance should be exposed with that port for native driver.

Configuration for port and other settings for native driver are in *different section* that official one, 
even if some of them overlap by default.

Native configuration supports all native driver settings.

Settings for native driver are available under *clickhouse.native* prefix as per example below.

Check [ClickHouse Native settings file](https://github.com/housepower/ClickHouse-Native-JDBC/blob/master/src/main/java/com/github/housepower/jdbc/settings/SettingKey.java) 
for info about all parameters.
```yaml
clickhouse:
  native:
    address: 127.0.0.1         # default - 127.0.0.1 (or equal to official driver config)
    port: 9000                 # default - 9000
    database: default          # default - default   (or equal to official driver config)
  ...
```

Some settings are equivalent to [Official ClickHouse driver configuration](#configuring-clickhouse-official-driver) by default:
* http_receive_timeout - equal to official connectionTimeout (default)
* http_send_timeout - equal to official connectionTimeout (default)
* connect_timeout - equal to official connectionTimeout (default)
* query_timeout - equal to official connectionTimeout multiplied by 1000 (default)
* connect_timeout_with_failover_ms - equal to official connectionTimeout (default)
* max_read_buffer_size - equal to official bufferSize (default)
* use_client_time_zone - false (default)

#### Database Initialization

There is an option to initialize database if it doesn't exist on startup via *createDatabaseIfNotExist* option.

Usage:

```yaml
clickhouse:
  createDatabaseIfNotExist: true        # default - false
```

### Health Check

Health check for ClickHouse is provided and is *turned on* by default.

Micronaut health check is part of [Micronaut Health Endpoint](https://docs.micronaut.io/latest/guide/index.html#healthEndpoint).

Example of ClickHouse health:

```json
{
  "name": "service",
  "status": "UP",
  "details": {
    "clickhouse": {
      "name": "clickhouse",
      "status": "UP",
      "details": {
        "database": "default"
      }
    }
  }
}
```

Where *database* name service is connected same as [configuration says](#Configuration).

You can explicitly *turn off* health check.

```yaml
clickhouse:
  health:
    enabled: false      # default - true 
```

## Testing

For testing purposes you can use [ClickHouse TestContainer library](https://www.testcontainers.org/modules/databases/clickhouse/).

TestContainers allows you to use integration tests with real database in all docker friendly environments, 
check here for [TestContainers](https://www.testcontainers.org/).

## Version History

**1.0.2** - Dependencies updated.

**1.0.1** - Added all native driver settings for configuration, fixed native driver inject issues.

**1.0.0** - Initial version, [official driver](https://github.com/ClickHouse/clickhouse-jdbc) and [native driver](https://github.com/housepower/ClickHouse-Native-JDBC) drivers support, database initialization, health check.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
