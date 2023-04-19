package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.ClickHouseProtocol;
import com.clickhouse.client.config.ClickHouseDefaults;
import com.clickhouse.config.ClickHouseBufferingMode;
import com.clickhouse.data.ClickHouseFormat;
import io.micronaut.core.annotation.Generated;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * ClickHouse Official JDBC Options
 *
 * @author Anton Kurako (GoodforGod)
 * @since 15.04.2023
 */
@Generated // to exclude from JaCoCo
public class ClickHouseJdbcOptions {

    private String host = (String) ClickHouseDefaults.HOST.getDefaultValue();
    private ClickHouseProtocol protocol = (ClickHouseProtocol) ClickHouseDefaults.PROTOCOL.getDefaultValue();
    private String user = (String) ClickHouseDefaults.USER.getDefaultValue();
    private String password = (String) ClickHouseDefaults.PASSWORD.getDefaultValue();
    private String database = (String) ClickHouseDefaults.DATABASE.getDefaultValue();
    private int maxThreads = (int) ClickHouseDefaults.MAX_THREADS.getDefaultValue();
    private ClickHouseFormat format = (ClickHouseFormat) ClickHouseDefaults.FORMAT.getDefaultValue();
    private boolean async = (boolean) ClickHouseDefaults.ASYNC.getDefaultValue();
    private boolean autoSession = (boolean) ClickHouseDefaults.AUTO_SESSION.getDefaultValue();
    private ClickHouseBufferingMode buffering = (ClickHouseBufferingMode) ClickHouseDefaults.BUFFERING.getDefaultValue();
    private int maxSchedulerThreads = (int) ClickHouseDefaults.MAX_SCHEDULER_THREADS.getDefaultValue();
    private int maxRequests = (int) ClickHouseDefaults.MAX_REQUESTS.getDefaultValue();
    private RoundingMode roundingMode = (RoundingMode) ClickHouseDefaults.ROUNDING_MODE.getDefaultValue();
    private Duration threadKeepalive = Duration.ofMillis((long) ClickHouseDefaults.THREAD_KEEPALIVE_TIMEOUT.getDefaultValue());
    private String serverTimeZone = (String) ClickHouseDefaults.SERVER_TIME_ZONE.getDefaultValue();
    private String serverVersion = (String) ClickHouseDefaults.SERVER_VERSION.getDefaultValue();
    private boolean srvResolve = (boolean) ClickHouseDefaults.SRV_RESOLVE.getDefaultValue();
    private String sslCertificateType = (String) ClickHouseDefaults.SSL_CERTIFICATE_TYPE.getDefaultValue();
    private String sslKeyAlgorithm = (String) ClickHouseDefaults.SSL_KEY_ALGORITHM.getDefaultValue();
    private String sslProtocol = (String) ClickHouseDefaults.SSL_PROTOCOL.getDefaultValue();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ClickHouseProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(ClickHouseProtocol protocol) {
        this.protocol = protocol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public ClickHouseFormat getFormat() {
        return format;
    }

    public void setFormat(ClickHouseFormat format) {
        this.format = format;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isAutoSession() {
        return autoSession;
    }

    public void setAutoSession(boolean autoSession) {
        this.autoSession = autoSession;
    }

    public ClickHouseBufferingMode getBuffering() {
        return buffering;
    }

    public void setBuffering(ClickHouseBufferingMode buffering) {
        this.buffering = buffering;
    }

    public int getMaxSchedulerThreads() {
        return maxSchedulerThreads;
    }

    public void setMaxSchedulerThreads(int maxSchedulerThreads) {
        this.maxSchedulerThreads = maxSchedulerThreads;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    public Duration getThreadKeepalive() {
        return threadKeepalive;
    }

    public void setThreadKeepalive(Duration threadKeepalive) {
        this.threadKeepalive = threadKeepalive;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public boolean isSrvResolve() {
        return srvResolve;
    }

    public void setSrvResolve(boolean srvResolve) {
        this.srvResolve = srvResolve;
    }

    public String getSslCertificateType() {
        return sslCertificateType;
    }

    public void setSslCertificateType(String sslCertificateType) {
        this.sslCertificateType = sslCertificateType;
    }

    public String getSslKeyAlgorithm() {
        return sslKeyAlgorithm;
    }

    public void setSslKeyAlgorithm(String sslKeyAlgorithm) {
        this.sslKeyAlgorithm = sslKeyAlgorithm;
    }

    public String getSslProtocol() {
        return sslProtocol;
    }

    public void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public Map<String, Object> getAsMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ClickHouseDefaults.HOST.getKey(), host);
        map.put(ClickHouseDefaults.PROTOCOL.getKey(), protocol);
        map.put(ClickHouseDefaults.USER.getKey(), user);
        if (password != null) {
            map.put(ClickHouseDefaults.PASSWORD.getKey(), password);
        }
        map.put(ClickHouseDefaults.DATABASE.getKey(), database);
        map.put(ClickHouseDefaults.MAX_THREADS.getKey(), maxThreads);
        map.put(ClickHouseDefaults.FORMAT.getKey(), format.name());
        map.put(ClickHouseDefaults.ASYNC.getKey(), async);
        map.put(ClickHouseDefaults.AUTO_SESSION.getKey(), autoSession);
        map.put(ClickHouseDefaults.BUFFERING.getKey(), buffering.name());
        map.put(ClickHouseDefaults.MAX_SCHEDULER_THREADS.getKey(), format);
        map.put(ClickHouseDefaults.MAX_REQUESTS.getKey(), maxRequests);
        map.put(ClickHouseDefaults.ROUNDING_MODE.getKey(), roundingMode.name());
        map.put(ClickHouseDefaults.THREAD_KEEPALIVE_TIMEOUT.getKey(), threadKeepalive.toMillis());
        map.put(ClickHouseDefaults.SERVER_TIME_ZONE.getKey(), serverTimeZone);
        map.put(ClickHouseDefaults.SERVER_VERSION.getKey(), serverVersion);
        map.put(ClickHouseDefaults.SRV_RESOLVE.getKey(), srvResolve);
        map.put(ClickHouseDefaults.SSL_CERTIFICATE_TYPE.getKey(), sslCertificateType);
        map.put(ClickHouseDefaults.SSL_KEY_ALGORITHM.getKey(), sslKeyAlgorithm);
        map.put(ClickHouseDefaults.SSL_PROTOCOL.getKey(), sslProtocol);

        return map;
    }

    @Override
    public String toString() {
        return getAsMap().toString();
    }
}
