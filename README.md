# Micronaut ClickHouse Configuration

![Java CI](https://github.com/GoodforGod/micronaut-clickhouse/workflows/Java%20CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-clickhouse&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-clickhouse)

This project includes integration between Micronaut and ClickHouse, autocompletion for configuration, official & native driver support, health check and more.

## Dependency :rocket:

**Gradle**
```groovy
dependencies {
    compile 'com.github.goodforgod:micronaut-clickhouse:2.2.1'
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>micronaut-clickhouse</artifactId>
    <version>2.2.1</version>
</dependency>
```

Starting from version *2.0.0* library ships for *Micronaut 2*.

Starting from version *2.1.0* Java 11+ is required (previous version 1.8+ compatible).

Last release for **Micronaut 1** is [version *1.0.2*](https://github.com/GoodforGod/micronaut-clickhouse/releases/tag/v1.0.2).

## Configuration

Includes a configuration to automatically configure official [ClickHouse Java drive](https://github.com/ClickHouse/clickhouse-jdbc)
or [ClickHouse Native Driver](https://github.com/housepower/ClickHouse-Native-JDBC). 
Just configure the host, port, credentials or url in *application.yml*.

```yaml
clickhouse:
  host: 127.0.0.1       # default - 127.0.0.1
  port: 8529            # default - 8529
  database: default     # default - default
  native:
    port: 9000          # default - 9000
```

## Official Driver

### Connections

Connections are injected as [**Prototypes**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) beans remember that while using them.

```java
@Inject
private ru.yandex.clickhouse.ClickHouseConnection officialConnection;
                                        // both are equally correct injections
@Named("clickhouse")
@Inject
private ru.yandex.clickhouse.ClickHouseConnection officialConnection;
```

Or via Java standard SQL interfaces (you may have to annotate connection named if you have other SQL connection beans around):

```java
@Named("clickhouse")
@Inject
private java.sql.Connection officialConnection;
```

In case you want to inject **[Singleton](https://docs.micronaut.io/latest/guide/index.html#builtInScopes)**
connections, you can specify @Named *prototype* and connection prototype bean will be injected.

```java
@Named("clickhouse-singleton")
@Inject
private java.sql.Connection officialConnection;
```

### Balanced DataSource

javax.sql.DataSource with balanced are injected as [**Singleton**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) beans remember that while using them.

```java
@Inject
private ru.yandex.clickhouse.BalancedClickhouseDataSource officialDataSource;
```

Or via Java standard SQL interfaces (you may have to annotate connection named if you have other SQL connection beans around):

```java
@Named("clickhouse")
@Inject
private java.sql.DataSource officialDataSource;
```

### Configuring ClickHouse Official Driver

All configs are provided via **full autocompletion**.

Official Configuration supports all available ClickHouse driver settings.

Check [ClickHouse Official settings file](https://github.com/ClickHouse/clickhouse-jdbc/blob/master/src/main/java/ru/yandex/clickhouse/settings/ClickHouseProperties.java)
for info about all parameters.
```yaml
clickhouse:
  url: jdbc:clickhouse://localhost:8529/default?compress=1
  host: 127.0.0.1       # default - 127.0.0.1
  port: 8529            # default - 8529
  database: default     # default - default
  async: true                           # default - false
  ssl: true                             # default - false
  maxRedirects: 5
  ...
```

You can specify connection only with URL and combine additional properties with URL:
```yaml
clickhouse:
  url: jdbc:clickhouse://localhost:8529/default?compress=1
  ssl: true                             # default - false
  maxRedirects: 5
  ...
```

Final connection URL in this case will be:
```text
jdbc:clickhouse://localhost:8529/default?compress=1&maxRedirects=5&ssl=true
```

You can also specify to use only URL as provided:
```yaml
clickhouse:
  url: jdbc:clickhouse://localhost:8529,localhost:8530/default?compress=1
  use-raw-url: true
  ssl: true                             # default - false
  maxRedirects: 5
  ...
```

Final connection URL in this case will be (additional properties out side of URL will be ignored):
```text
jdbc:clickhouse://localhost:8529,localhost:8530/default?compress=1
```

## Native Driver

### Connections

Connections are injected as [**Prototypes**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) beans remember that while using them.

```java
@Inject
private com.github.housepower.jdbc.ClickHouseConnection nativeConnection;
```

Or via Java standard SQL interfaces (you may have to annotate connection named if you have other SQL connection beans around):

```java
@Named("clickhouse-native")
@Inject
private java.sql.Connection nativeConnection;
```

In case you want to inject **[Singleton](https://docs.micronaut.io/latest/guide/index.html#builtInScopes)**
connections, you can specify @Named *prototype* and connection prototype bean will be injected.

```java
@Named("clickhouse-native-singleton")
@Inject
private java.sql.Connection nativeConnection;
```

### Balanced DataSource

javax.sql.DataSource with balanced are injected as [**Singleton**](https://docs.micronaut.io/latest/guide/index.html#builtInScopes) beans remember that while using them.

```java
@Inject
private com.github.housepower.jdbc.BalancedClickhouseDataSource nativeDataSource;
```

Or via Java standard SQL interfaces (you may have to annotate connection named if you have other SQL connection beans around):

```java
@Named("clickhouse-native")
@Inject
private java.sql.DataSource nativeDataSource;
```

### Configuring ClickHouse Native Driver

All configs are provided via **full autocompletion**.

Settings for native driver are available under *clickhouse.native* prefix as per example below.

**Remember** that native driver uses **port different from official** driver, which is default to *9000* and not *8529*.
So your ClickHouse instance should be exposed with that port for native driver.

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

You can specify connection only with URL and combine additional properties with URL:
```yaml
clickhouse:
  native:
    url: jdbc:clickhouse://localhost:9000/default?compress=1
    ssl: true                             # default - false
    maxRedirects: 5
    ...
```

Final connection URL in this case will be:
```text
jdbc:clickhouse://localhost:9000/default?compress=1&maxRedirects=5&ssl=true
```

You can also specify to use only URL as provided:
```yaml
clickhouse:
  native:
    url: jdbc:clickhouse://localhost:8529/default?compress=1
    use-raw-url: true
    ssl: true                             # default - false
    maxRedirects: 5
    ...
```

Final connection URL in this case will be (additional properties out side of URL will be ignored):
```text
jdbc:clickhouse://localhost:8529/default?compress=1
```


## Database Initialization

There is an option to initialize database if it doesn't exist on startup via *createDatabaseIfNotExist* option.

```yaml
clickhouse:
  create-database-if-not-exist: true    # default - false
```

Default timeout for operation set to 10 seconds, if you want to specify timeout *in seconds* for database creation
on startup you can set it via property.

```yaml
clickhouse:
  create-database-timeout-in-millis: 500 # default - 10000
```

## Health Check

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

TestContainers allows you to use integration tests against real database in all docker friendly environments, 
check here for [TestContainers](https://www.testcontainers.org/).

## Version History

**2.2.1** - Micronaut updated to 2.5.4, official driver updated to 0.3.1

**2.2.0** - Balanced official & native DataSource added, autocomplete for native configuration, url configuration for official & native driver, Singleton connection -> Prototype connection by default, Micronaut updated to 2.4.1, configuration improvements.

**2.1.0** - Java updated to 11, Micronaut updated to 2.1.1.

**2.0.0** - Micronaut 2 support, database init timeout property added, dependency updated.

**1.0.2** - Dependencies updated.

**1.0.1** - Added all native driver settings for configuration, fixed native driver inject issues.

**1.0.0** - Initial version, [official driver](https://github.com/ClickHouse/clickhouse-jdbc) and [native driver](https://github.com/housepower/ClickHouse-Native-JDBC) drivers support, database initialization, health check.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
