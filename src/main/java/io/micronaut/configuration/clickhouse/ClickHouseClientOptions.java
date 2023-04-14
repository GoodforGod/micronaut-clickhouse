package io.micronaut.configuration.clickhouse;

import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.client.config.ClickHouseSslMode;
import com.clickhouse.config.ClickHouseBufferingMode;
import com.clickhouse.data.ClickHouseCompression;
import com.clickhouse.data.ClickHouseFormat;
import com.clickhouse.data.ClickHouseVersion;

import java.util.*;

/**
 * ClickHouse Official Driver configuration class.
 *
 * @see com.clickhouse.client.config.ClickHouseClientOption
 * @author Anton Kurako (GoodforGod)
 * @since 13.04.2023
 */
public class ClickHouseClientOptions {

    private boolean async = (boolean) ClickHouseClientOption.ASYNC.getDefaultValue();
    private boolean autoDiscovery = (boolean) ClickHouseClientOption.AUTO_DISCOVERY.getDefaultValue();
    private String clientName = (String) ClickHouseClientOption.CLIENT_NAME.getDefaultValue();
    private boolean compressRequest = (boolean) ClickHouseClientOption.COMPRESS.getDefaultValue();
    private ClickHouseCompression compressAlgorithm = (ClickHouseCompression) ClickHouseClientOption.COMPRESS_ALGORITHM.getDefaultValue();
    private int compressLevel = (int) ClickHouseClientOption.COMPRESS_LEVEL.getDefaultValue();
    private boolean decompressResponse = (boolean) ClickHouseClientOption.DECOMPRESS.getDefaultValue();
    private ClickHouseCompression decompressAlgorithm = (ClickHouseCompression) ClickHouseClientOption.DECOMPRESS_ALGORITHM.getDefaultValue();
    private int decompressLevel = (int) ClickHouseClientOption.DECOMPRESS_LEVEL.getDefaultValue();
    private int connectionTimeout = (int) ClickHouseClientOption.CONNECTION_TIMEOUT.getDefaultValue();
    private String database = null;
    private ClickHouseFormat format = (ClickHouseFormat) ClickHouseClientOption.FORMAT.getDefaultValue();
    private int maxBufferSize = (int) ClickHouseClientOption.MAX_BUFFER_SIZE.getDefaultValue();
    private int bufferSize = (int) ClickHouseClientOption.BUFFER_SIZE.getDefaultValue();
    private int bufferQueueVariation = (int) ClickHouseClientOption.BUFFER_QUEUE_VARIATION.getDefaultValue();
    private int readBufferSize = (int) ClickHouseClientOption.READ_BUFFER_SIZE.getDefaultValue();
    private int writeBufferSize = (int) ClickHouseClientOption.WRITE_BUFFER_SIZE.getDefaultValue();
    private int requestChunkSize = (int) ClickHouseClientOption.REQUEST_CHUNK_SIZE.getDefaultValue();
    private ClickHouseBufferingMode requestBuffering = (ClickHouseBufferingMode) ClickHouseClientOption.REQUEST_BUFFERING.getDefaultValue();
    private ClickHouseBufferingMode responseBuffering = (ClickHouseBufferingMode) ClickHouseClientOption.RESPONSE_BUFFERING.getDefaultValue();
    private int maxExecutionTime = (int) ClickHouseClientOption.MAX_EXECUTION_TIME.getDefaultValue();
    private int maxQueuedBuffers = (int) ClickHouseClientOption.MAX_QUEUED_BUFFERS.getDefaultValue();
    private int maxQueuedRequests= (int) ClickHouseClientOption.MAX_QUEUED_REQUESTS.getDefaultValue();
    private long maxResultRows = (long) ClickHouseClientOption.MAX_RESULT_ROWS.getDefaultValue();
    private int maxThreads = (int) ClickHouseClientOption.MAX_THREADS_PER_CLIENT.getDefaultValue();
    private String productName = (String) ClickHouseClientOption.PRODUCT_NAME.getDefaultValue();
    private int nodeCheckInterval = (int) ClickHouseClientOption.NODE_CHECK_INTERVAL.getDefaultValue();
    private int failover = (int) ClickHouseClientOption.FAILOVER.getDefaultValue();
    private int retry = (int) ClickHouseClientOption.RETRY.getDefaultValue();
    private boolean repeatOnSessionLock = (boolean) ClickHouseClientOption.REPEAT_ON_SESSION_LOCK.getDefaultValue();
    private boolean reuseValueWrapper = (boolean) ClickHouseClientOption.REUSE_VALUE_WRAPPER.getDefaultValue();
    private int sessionTimeout = (int) ClickHouseClientOption.SESSION_TIMEOUT.getDefaultValue();
    private boolean sessionCheck = (boolean) ClickHouseClientOption.SESSION_CHECK.getDefaultValue();
    private int socketTimeout = (int) ClickHouseClientOption.SOCKET_TIMEOUT.getDefaultValue();
    private boolean ssl = (boolean) ClickHouseClientOption.SSL.getDefaultValue();
    private ClickHouseSslMode sslMode = (ClickHouseSslMode) ClickHouseClientOption.SSL_MODE.getDefaultValue();
    private String sslRootCert = null;
    private String sslCert = null;
    private String sslKey = null;
    private int transactionTimeout = (int) ClickHouseClientOption.TRANSACTION_TIMEOUT.getDefaultValue();
    private boolean widenUnsignedTypes = (boolean) ClickHouseClientOption.WIDEN_UNSIGNED_TYPES.getDefaultValue();
    private boolean useBinaryString = (boolean) ClickHouseClientOption.USE_BINARY_STRING.getDefaultValue();
    private boolean useBlockingQueue = (boolean) ClickHouseClientOption.USE_BLOCKING_QUEUE.getDefaultValue();
    private boolean useObjectsInArray = (boolean) ClickHouseClientOption.USE_OBJECTS_IN_ARRAYS.getDefaultValue();
    private boolean useNoProxy = (boolean) ClickHouseClientOption.USE_NO_PROXY.getDefaultValue();
    private boolean useServerTimeZone = (boolean) ClickHouseClientOption.USE_SERVER_TIME_ZONE.getDefaultValue();
    private boolean useServerTimeZoneForDates = (boolean) ClickHouseClientOption.USE_SERVER_TIME_ZONE_FOR_DATES.getDefaultValue();
    private String timeZoneId;

    private Map<String, String> customSettings = new HashMap<>();

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isAutoDiscovery() {
        return autoDiscovery;
    }

    public void setAutoDiscovery(boolean autoDiscovery) {
        this.autoDiscovery = autoDiscovery;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isCompressRequest() {
        return compressRequest;
    }

    public void setCompressRequest(boolean compressRequest) {
        this.compressRequest = compressRequest;
    }

    public ClickHouseCompression getCompressAlgorithm() {
        return compressAlgorithm;
    }

    public void setCompressAlgorithm(ClickHouseCompression compressAlgorithm) {
        this.compressAlgorithm = compressAlgorithm;
    }

    public int getCompressLevel() {
        return compressLevel;
    }

    public void setCompressLevel(int compressLevel) {
        this.compressLevel = compressLevel;
    }

    public boolean isDecompressResponse() {
        return decompressResponse;
    }

    public void setDecompressResponse(boolean decompressResponse) {
        this.decompressResponse = decompressResponse;
    }

    public ClickHouseCompression getDecompressAlgorithm() {
        return decompressAlgorithm;
    }

    public void setDecompressAlgorithm(ClickHouseCompression decompressAlgorithm) {
        this.decompressAlgorithm = decompressAlgorithm;
    }

    public int getDecompressLevel() {
        return decompressLevel;
    }

    public void setDecompressLevel(int decompressLevel) {
        this.decompressLevel = decompressLevel;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public ClickHouseFormat getFormat() {
        return format;
    }

    public void setFormat(ClickHouseFormat format) {
        this.format = format;
    }

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getBufferQueueVariation() {
        return bufferQueueVariation;
    }

    public void setBufferQueueVariation(int bufferQueueVariation) {
        this.bufferQueueVariation = bufferQueueVariation;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public int getWriteBufferSize() {
        return writeBufferSize;
    }

    public void setWriteBufferSize(int writeBufferSize) {
        this.writeBufferSize = writeBufferSize;
    }

    public int getRequestChunkSize() {
        return requestChunkSize;
    }

    public void setRequestChunkSize(int requestChunkSize) {
        this.requestChunkSize = requestChunkSize;
    }

    public ClickHouseBufferingMode getRequestBuffering() {
        return requestBuffering;
    }

    public void setRequestBuffering(ClickHouseBufferingMode requestBuffering) {
        this.requestBuffering = requestBuffering;
    }

    public ClickHouseBufferingMode getResponseBuffering() {
        return responseBuffering;
    }

    public void setResponseBuffering(ClickHouseBufferingMode responseBuffering) {
        this.responseBuffering = responseBuffering;
    }

    public int getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime(int maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    public int getMaxQueuedBuffers() {
        return maxQueuedBuffers;
    }

    public void setMaxQueuedBuffers(int maxQueuedBuffers) {
        this.maxQueuedBuffers = maxQueuedBuffers;
    }

    public int getMaxQueuedRequests() {
        return maxQueuedRequests;
    }

    public void setMaxQueuedRequests(int maxQueuedRequests) {
        this.maxQueuedRequests = maxQueuedRequests;
    }

    public long getMaxResultRows() {
        return maxResultRows;
    }

    public void setMaxResultRows(long maxResultRows) {
        this.maxResultRows = maxResultRows;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNodeCheckInterval() {
        return nodeCheckInterval;
    }

    public void setNodeCheckInterval(int nodeCheckInterval) {
        this.nodeCheckInterval = nodeCheckInterval;
    }

    public int getFailover() {
        return failover;
    }

    public void setFailover(int failover) {
        this.failover = failover;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public boolean isRepeatOnSessionLock() {
        return repeatOnSessionLock;
    }

    public void setRepeatOnSessionLock(boolean repeatOnSessionLock) {
        this.repeatOnSessionLock = repeatOnSessionLock;
    }

    public boolean isReuseValueWrapper() {
        return reuseValueWrapper;
    }

    public void setReuseValueWrapper(boolean reuseValueWrapper) {
        this.reuseValueWrapper = reuseValueWrapper;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public boolean isSessionCheck() {
        return sessionCheck;
    }

    public void setSessionCheck(boolean sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public ClickHouseSslMode getSslMode() {
        return sslMode;
    }

    public void setSslMode(ClickHouseSslMode sslMode) {
        this.sslMode = sslMode;
    }

    public String getSslRootCert() {
        return sslRootCert;
    }

    public void setSslRootCert(String sslRootCert) {
        this.sslRootCert = sslRootCert;
    }

    public String getSslCert() {
        return sslCert;
    }

    public void setSslCert(String sslCert) {
        this.sslCert = sslCert;
    }

    public String getSslKey() {
        return sslKey;
    }

    public void setSslKey(String sslKey) {
        this.sslKey = sslKey;
    }

    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public boolean isWidenUnsignedTypes() {
        return widenUnsignedTypes;
    }

    public void setWidenUnsignedTypes(boolean widenUnsignedTypes) {
        this.widenUnsignedTypes = widenUnsignedTypes;
    }

    public boolean isUseBinaryString() {
        return useBinaryString;
    }

    public void setUseBinaryString(boolean useBinaryString) {
        this.useBinaryString = useBinaryString;
    }

    public boolean isUseBlockingQueue() {
        return useBlockingQueue;
    }

    public void setUseBlockingQueue(boolean useBlockingQueue) {
        this.useBlockingQueue = useBlockingQueue;
    }

    public boolean isUseObjectsInArray() {
        return useObjectsInArray;
    }

    public void setUseObjectsInArray(boolean useObjectsInArray) {
        this.useObjectsInArray = useObjectsInArray;
    }

    public boolean isUseNoProxy() {
        return useNoProxy;
    }

    public void setUseNoProxy(boolean useNoProxy) {
        this.useNoProxy = useNoProxy;
    }

    public boolean isUseServerTimeZone() {
        return useServerTimeZone;
    }

    public void setUseServerTimeZone(boolean useServerTimeZone) {
        this.useServerTimeZone = useServerTimeZone;
    }

    public boolean isUseServerTimeZoneForDates() {
        return useServerTimeZoneForDates;
    }

    public void setUseServerTimeZoneForDates(boolean useServerTimeZoneForDates) {
        this.useServerTimeZoneForDates = useServerTimeZoneForDates;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public Map<String, String> getCustomSettings() {
        return customSettings;
    }

    public void setCustomSettings(Map<String, String> customSettings) {
        this.customSettings = customSettings;
    }

    public Map<String, Object> getAsMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ClickHouseClientOption.ASYNC.getKey(), async);
        map.put(ClickHouseClientOption.AUTO_DISCOVERY.getKey(), autoDiscovery);
        map.put(ClickHouseClientOption.CLIENT_NAME.getKey(), clientName);
        map.put(ClickHouseClientOption.COMPRESS.getKey(), compressRequest);
        map.put(ClickHouseClientOption.COMPRESS_ALGORITHM.getKey(), compressAlgorithm);
        map.put(ClickHouseClientOption.COMPRESS_LEVEL.getKey(), compressLevel);
        map.put(ClickHouseClientOption.DECOMPRESS.getKey(), decompressResponse);
        map.put(ClickHouseClientOption.DECOMPRESS_ALGORITHM.getKey(), decompressAlgorithm);
        map.put(ClickHouseClientOption.DECOMPRESS_LEVEL.getKey(), decompressLevel);
        map.put(ClickHouseClientOption.CONNECTION_TIMEOUT.getKey(), connectionTimeout);
        if(database != null) {
            map.put(ClickHouseClientOption.DATABASE.getKey(), database);
        }
        map.put(ClickHouseClientOption.FORMAT.getKey(), format);
        map.put(ClickHouseClientOption.MAX_BUFFER_SIZE.getKey(), maxBufferSize);
        map.put(ClickHouseClientOption.BUFFER_SIZE.getKey(), bufferSize);
        map.put(ClickHouseClientOption.BUFFER_QUEUE_VARIATION.getKey(), bufferQueueVariation);
        map.put(ClickHouseClientOption.READ_BUFFER_SIZE.getKey(), readBufferSize);
        map.put(ClickHouseClientOption.WRITE_BUFFER_SIZE.getKey(), writeBufferSize);
        map.put(ClickHouseClientOption.REQUEST_CHUNK_SIZE.getKey(), requestChunkSize);
        map.put(ClickHouseClientOption.REQUEST_BUFFERING.getKey(), requestBuffering);
        map.put(ClickHouseClientOption.RESPONSE_BUFFERING.getKey(), responseBuffering);
        map.put(ClickHouseClientOption.MAX_EXECUTION_TIME.getKey(), maxExecutionTime);
        map.put(ClickHouseClientOption.MAX_QUEUED_BUFFERS.getKey(), maxQueuedBuffers);
        map.put(ClickHouseClientOption.MAX_QUEUED_REQUESTS.getKey(), maxQueuedRequests);
        map.put(ClickHouseClientOption.MAX_RESULT_ROWS.getKey(), maxResultRows);
        map.put(ClickHouseClientOption.MAX_THREADS_PER_CLIENT.getKey(), maxThreads);
        map.put(ClickHouseClientOption.PRODUCT_NAME.getKey(), productName);
        map.put(ClickHouseClientOption.NODE_CHECK_INTERVAL.getKey(), nodeCheckInterval);
        map.put(ClickHouseClientOption.FAILOVER.getKey(), failover);
        map.put(ClickHouseClientOption.RETRY.getKey(), retry);
        map.put(ClickHouseClientOption.REPEAT_ON_SESSION_LOCK.getKey(), repeatOnSessionLock);
        map.put(ClickHouseClientOption.REUSE_VALUE_WRAPPER.getKey(), reuseValueWrapper);
        map.put(ClickHouseClientOption.SESSION_TIMEOUT.getKey(), sessionTimeout);
        map.put(ClickHouseClientOption.SESSION_CHECK.getKey(), sessionCheck);
        map.put(ClickHouseClientOption.SOCKET_TIMEOUT.getKey(), socketTimeout);
        map.put(ClickHouseClientOption.SSL.getKey(), ssl);
        map.put(ClickHouseClientOption.SSL_MODE.getKey(), sslMode);
        if(sslRootCert != null) {
            map.put(ClickHouseClientOption.SSL_ROOT_CERTIFICATE.getKey(), sslRootCert);
        }
        if(sslCert != null) {
            map.put(ClickHouseClientOption.SSL_CERTIFICATE.getKey(), sslCert);
        }
        if(sslKey != null) {
            map.put(ClickHouseClientOption.SSL_KEY.getKey(), sslKey);
        }
        map.put(ClickHouseClientOption.TRANSACTION_TIMEOUT.getKey(), transactionTimeout);
        map.put(ClickHouseClientOption.WIDEN_UNSIGNED_TYPES.getKey(), widenUnsignedTypes);
        map.put(ClickHouseClientOption.USE_BINARY_STRING.getKey(), useBinaryString);
        map.put(ClickHouseClientOption.USE_BLOCKING_QUEUE.getKey(), useBlockingQueue);
        map.put(ClickHouseClientOption.USE_OBJECTS_IN_ARRAYS.getKey(), useObjectsInArray);
        map.put(ClickHouseClientOption.USE_NO_PROXY.getKey(), useNoProxy);
        map.put(ClickHouseClientOption.USE_SERVER_TIME_ZONE.getKey(), useServerTimeZone);
        map.put(ClickHouseClientOption.USE_SERVER_TIME_ZONE_FOR_DATES.getKey(), useServerTimeZoneForDates);
        if(timeZoneId != null) {
            map.put(ClickHouseClientOption.USE_TIME_ZONE.getKey(), TimeZone.getTimeZone(timeZoneId));
        }

        return map;
    }

    @Override
    public String toString() {
        return getAsMap().toString();
    }
}
