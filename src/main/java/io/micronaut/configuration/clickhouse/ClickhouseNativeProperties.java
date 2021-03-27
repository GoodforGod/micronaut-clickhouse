package io.micronaut.configuration.clickhouse;

import com.github.housepower.jdbc.serde.SettingType;
import com.github.housepower.jdbc.settings.SettingKey;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Anton Kurako (GoodforGod)
 * @see io.micronaut.configuration.clickhouse.ClickHouseNativeConfiguration
 * @since 20.3.2021
 */
public class ClickhouseNativeProperties {

    /**
     * The actual size of the block to compress, if the uncompressed data less than
     * maxCompressBlockSize is no less than this value and no less than the volume
     * of data for one mark.
     */
    private Integer minCompressBlockSize;

    /**
     * The maximum Integer of blocks of uncompressed data before compressing for
     * writing to a table.
     */
    private SettingKey maxCompressBlockSize;

    /**
     * Maximum block size for reading
     */
    private Integer maxBlockSize;

    /**
     * The maximum block size for insertion, if we control the creation of blocks
     * for insertion.
     */
    private Integer maxInsertBlockSize;

    /**
     * Squash blocks passed to INSERT query to specified size in rows, if blocks are
     * not big enough.
     */
    private Integer minInsertBlockSizeRows;

    /**
     * Squash blocks passed to INSERT query to specified size in bytes, if blocks
     * are not big enough.
     */
    private Integer minInsertBlockSizeBytes;

    /**
     * The maximum size of the buffer to read from the filesystem.
     */
    private Integer maxReadBufferSize;

    /**
     * The maximum number of connections for distributed processing of one query
     * (should be greater than maxThreads).
     */
    private Integer maxDistributedConnections;

    /**
     * Which part of the query can be read into RAM for parsing (the remaining data
     * for INSERT, if any, is read later)
     */
    private Integer maxQuerySize;

    /**
     * The interval in microseconds to check if the request is cancelled, and to
     * send progress info.
     */
    private Integer interactiveDelay;

    /**
     * Connection timeout if there are no replicas.
     */
    private Duration connectTimeout;

    /**
     * Connection timeout for selecting first healthy replica.
     */
    private Duration connectTimeoutWithFailoverMs;

    /**
     * The wait time in the request queue, if the number of concurrent requests
     * exceeds the maximum.
     */
    private Duration queueMaxWaitMs;

    /**
     * Block at the query wait loop on the server for the specified number of
     * seconds.
     */
    private Integer pollInterval;

    /**
     * Maximum number of connections with one remote server in the pool.
     */
    private Integer distributedConnectionsPoolSize;

    /**
     * The maximum number of attempts to connect to replicas.
     */
    private Integer connectionsWithFailoverMaxTries;

    /**
     * Calculate minimums and maximums of the result columns. They can be output in
     * JSON-formats.
     */
    private Boolean extremes;

    /**
     * Whether to use the cache of uncompressed blocks.
     */
    private Boolean useUncompressedCache;

    /**
     * Whether the running request should be canceled with the same id as the new
     * one.
     */
    private Boolean replaceRunningQuery;

    /**
     * Number of threads performing background work for tables (for example, merging
     * in merge tree). Only has meaning at server startup.
     */
    private Integer backgroundPoolSize;

    /**
     * Number of threads performing background tasks for replicated tables. Only has
     * meaning at server startup.
     */
    private Integer backgroundSchedulePoolSize;

    /**
     * Sleep time for StorageDistributed DirectoryMonitors in case there is no work
     * or exception has been thrown.
     */
    private Duration distributedDirectoryMonitorSleepTimeMs;

    /**
     * Should StorageDistributed DirectoryMonitors try to batch individual inserts
     * into bigger ones.
     */
    private Boolean distributedDirectoryMonitorBatchInserts;

    /**
     * Allows disabling WHERE to PREWHERE optimization in SELECT queries from
     * MergeTree.
     */
    private Boolean optimizeMoveToPrewhere;

    /**
     * Wait for actions to manipulate the partitions. 0 - do not wait, 1 - wait for
     * execution only of itself, 2 - wait for everyone.
     */
    private Integer replicationAlterPartitionsSync;

    /**
     * Wait for actions to change the table structure within the specified number of
     * seconds. 0 - wait unlimited time.
     */
    private Integer replicationAlterColumnsTimeout;

    /**
     * The threshold for totalsMode = 'auto'.
     */
    private Float totalsAutoThreshold;

    /**
     * Whether query compilation is enabled.
     */
    private Boolean compile;

    /**
     * Compile some scalar functions and operators to native code.
     */
    private Boolean compileExpressions;

    /**
     * The number of structurally identical queries before they are compiled.
     */
    private Integer minCountToCompile;

    /**
     * From what number of keys, a two-level aggregation starts. 0 - the threshold
     * is not set.
     */
    private Integer groupByTwoLevelThreshold;

    /**
     * From what size of the aggregation state in bytes, a two-level aggregation
     * begins to be used. 0 - the threshold is not set. Two-level aggregation is
     * used when at least one of the thresholds is triggered.
     */
    private Integer groupByTwoLevelThresholdBytes;

    /**
     * Is the memory-saving mode of distributed aggregation enabled.
     */
    private Boolean distributedAggregationMemoryEfficient;

    /**
     * Number of threads to use for merge intermediate aggregation results in memory
     * efficient mode. When bigger, then more memory is consumed. 0 means - same as
     * 'maxThreads'.
     */
    private Integer aggregationMemoryEfficientMergeThreads;

    /**
     * The maximum number of threads to execute the request. By default, it is
     * determined automatically.
     */
    private Integer maxThreads;

    /**
     * The maximum number of replicas of each shard used when the query is executed.
     * For consistency (to get different parts of the same partition), this option
     * only works for the specified sampling key. The lag of the replicas is not
     * controlled.
     */
    private Integer maxParallelReplicas;

    /**
     * Silently skip unavailable shards.
     */
    private Boolean skipUnavailableShards;

    /**
     * Do not merge aggregation states from different servers for distributed query
     * processing - in case it is for certain that there are different keys on
     * different shards.
     */
    private Boolean distributedGroupByNoMerge;

    /**
     * If at least as many lines are read from one file, the reading can be
     * parallelized.
     */
    private Integer mergeTreeMinRowsForConcurrentRead;

    /**
     * You can skip reading more than that number of rows at the price of one seek
     * per file.
     */
    private Integer mergeTreeMinRowsForSeek;

    /**
     * If the index segment can contain the required keys, divide it into as many
     * parts and recursively check them.
     */
    private Integer mergeTreeCoarseIndexGranularity;

    /**
     * The maximum number of rows per request, to use the cache of uncompressed
     * data. If the request is large, the cache is not used. (For large queries not
     * to flush out the cache.)
     */
    private Integer mergeTreeMaxRowsToUseCache;

    /**
     * Distribute read from MergeTree over threads evenly, ensuring stable average
     * execution time of each thread within one read operation.
     */
    private Boolean mergeTreeUniformReadDistribution;

    /**
     * The maximum number of rows in MySQL batch insertion of the MySQL storage
     * engine
     */
    private Integer mysqlMaxRowsToInsert;

    /**
     * The minimum length of the expression `expr = x1 OR ... expr = xN` for
     * optimization
     */
    private Integer optimizeMinEqualityDisjunctionChainLength;

    /**
     * The minimum number of bytes for input/output operations is bypassing the page
     * cache. 0 - disabled.
     */
    private Integer minBytesToUseDirectIo;

    /**
     * Throw an exception if there is a partition key in a table, and it is not
     * used.
     */
    private Boolean forceIndexByDate;

    /**
     * Throw an exception if there is primary key in a table, and it is not used.
     */
    private Boolean forcePrimaryKey;

    /**
     * If the maximum size of markCache is exceeded, delete only records older than
     * markCacheMinLifetime seconds.
     */
    private Integer markCacheMinLifetime;

    /**
     * Allows you to use more sources than the number of threads - to more evenly
     * distribute work across threads. It is assumed that this is a temporary
     * solution, since it will be possible in the future to make the number of
     * sources equal to the number of threads, but for each source to dynamically
     * select available work for itself.
     */
    private Float maxStreamsToMaxThreadsRatio;

    /**
     * Allows you to select the level of ZSTD compression.
     */
    private Integer networkZstdCompressionLevel;

    /**
     * Priority of the query. 1 - the highest, higher value - lower priority; 0 - do
     * not use priorities.
     */
    private Integer priority;

    /**
     * Log requests and write the log to the system table.
     */
    private Boolean logQueries;

    /**
     * If query length is greater than specified threshold (in bytes), then cut
     * query when writing to query log. Also limit length of printed query in
     * ordinary text log.
     */
    private Integer logQueriesCutToLength;

    /**
     * The maximum number of concurrent requests per user.
     */
    private Integer maxConcurrentQueriesForUser;

    /**
     * For INSERT queries in the replicated table, specifies that deduplication of
     * insertings blocks should be preformed
     */
    private Boolean insertDeduplicate;

    /**
     * For INSERT queries in the replicated table, wait writing for the specified
     * number of replicas and linearize the addition of the data. 0 - disabled.
     */
    private Integer insertQuorum;

    /**
     * For SELECT queries from the replicated table, throw an exception if the
     * replica does not have a chunk written with the quorum; do not read the parts
     * that have not yet been written with the quorum.
     */
    private Integer selectSequentialConsistency;

    /**
     * The maximum number of different shards and the maximum number of replicas of
     * one shard in the `remote` function.
     */
    private Integer tableFunctionRemoteMaxAddresses;

    /**
     * Setting to reduce the number of threads in case of slow reads. Pay attention
     * only to reads that took at least that much time.
     */
    private Duration readBackoffMinLatencyMs;

    /**
     * Settings to reduce the number of threads in case of slow reads. Count events
     * when the read bandwidth is less than that many bytes per second.
     */
    private Integer readBackoffMaxThroughput;

    /**
     * Settings to reduce the number of threads in case of slow reads. Do not pay
     * attention to the event, if the previous one has passed less than a certain
     * amount of time.
     */
    private Duration readBackoffMinIntervalBetweenEventsMs;

    /**
     * Settings to reduce the number of threads in case of slow reads. The number of
     * events after which the number of threads will be reduced.
     */
    private Integer readBackoffMinEvents;

    /**
     * For testing of `exception safety` - throw an exception every time you
     * allocate memory with the specified probability.
     */
    private Float memoryTrackerFaultProbability;

    /**
     * Compress the result if the client over HTTP said that it understands data
     * compressed by gzip or deflate.
     */
    private Boolean enableHttpCompression;

    /**
     * Compression level - used if the client on HTTP said that it understands data
     * compressed by gzip or deflate.
     */
    private Integer httpZlibCompressionLevel;

    /**
     * If you uncompress the POST data from the client compressed by the native
     * format, do not check the checksum.
     */
    private Boolean httpNativeCompressionDisableChecksummingOnDecompress;

    /**
     * What aggregate function to use for implementation of count(DISTINCT ...)
     */
    private String countDistinctImplementation;

    /**
     * Write statistics about read rows, bytes, time elapsed in suitable output
     * formats.
     */
    private Boolean outputFormatWriteStatistics;

    /**
     * Write add http CORS header.
     */
    private Boolean addHttpCorsHeader;

    /**
     * Skip columns with unknown names from input data (it works for JSONEachRow and
     * TSKV formats).
     */
    private Boolean inputFormatSkipUnknownFields;

    /**
     * For Values format: if field could not be parsed by streaming parser, run SQL
     * parser and try to interpret it as SQL expression.
     */
    private Boolean inputFormatValuesInterpretExpressions;

    /**
     * Controls quoting of 64-bit integers in JSON output format.
     */
    private Boolean outputFormatJsonQuote_64bitIntegers;

    /**
     * Enables '+nan', '-nan', '+inf', '-inf' outputs in JSON output format.
     */
    private Boolean outputFormatJsonQuoteDenormals;

    /**
     * Rows limit for Pretty formats.
     */
    private Integer outputFormatPrettyMaxRows;

    /**
     * Use client timezone for interpreting DateTime string values, instead of
     * adopting server timezone.
     */
    private Boolean useClientTimeZone;

    /**
     * Send progress notifications using X-ClickHouse-Progress headers. Some clients
     * do not support high amount of HTTP headers (Python requests in particular),
     * so it is disabled by default.
     */
    private Boolean sendProgressInHttpHeaders;

    /**
     * Do not send HTTP headers X-ClickHouse-Progress more frequently than at each
     * specified interval.
     */
    private Integer httpHeadersProgressIntervalMs;

    /**
     * Do fsync after changing metadata for tables and databases (.sql files). Could
     * be disabled in case of poor latency on server with high load of DDL queries
     * and high load of disk subsystem.
     */
    private Boolean fsyncMetadata;

    /**
     * Maximum absolute amount of errors while reading text formats (like CSV, TSV).
     * In case of error, if both absolute and relative values are non-zero, and at
     * least absolute or relative amount of errors is lower than corresponding
     * value, will skip until next line and continue.
     */
    private Integer inputFormatAllowErrorsNum;

    /**
     * Maximum relative amount of errors while reading text formats (like CSV, TSV).
     * In case of error, if both absolute and relative values are non-zero, and at
     * least absolute or relative amount of errors is lower than corresponding
     * value, will skip until next line and continue.
     */
    private Float inputFormatAllowErrorsRatio;

    /**
     * Use NULLs for non-joined rows of outer JOINs. If false, use default value of
     * corresponding columns data type.
     */
    private Boolean joinUseNulls;

    /**
     * If set, distributed queries of Replicated tables will choose servers with
     * replication delay in seconds less than the specified value (not inclusive).
     * Zero means do not take delay into account.
     */
    private Integer maxReplicaDelayForDistributedQueries;

    /**
     * Suppose maxReplicaDelayForDistributedQueries is set and all replicas for the
     * queried table are stale. If this setting is enabled, the query will be
     * performed anyway, otherwise the error will be reported.
     */
    private Boolean fallbackToStaleReplicasForDistributedQueries;

    /**
     * Limit on max column size in block while reading. Helps to decrease cache
     * misses count. Should be close to L2 cache size.
     */
    private Integer preferredMaxColumnInBlockSizeBytes;

    /**
     * If setting is enabled, insert query into distributed waits until data will be
     * sent to all nodes in cluster.
     */
    private Boolean insertDistributedSync;

    /**
     * Timeout for insert query into distributed. Setting is used only with
     * insertDistributedSync enabled. Zero value means no timeout.
     */
    private Integer insertDistributedTimeout;

    /**
     * Timeout for DDL query responses from all hosts in cluster. Negative value
     * means infinite.
     */
    private Integer distributedDdlTaskTimeout;

    /**
     * Timeout for flushing data from streaming storages.
     */
    private Duration streamFlushIntervalMs;

    /**
     * Schema identifier (used by schema-based formats)
     */
    private String formatSchema;

    /**
     * If setting is enabled, Allow materialized columns in INSERT.
     */
    private Boolean insertAllowMaterializedColumns;

    /**
     * HTTP connection timeout.
     */
    private Duration httpConnectionTimeout;

    /**
     * HTTP send timeout
     */
    private Duration httpSendTimeout;

    /**
     * HTTP receive timeout
     */
    private Duration httpReceiveTimeout;

    /**
     * If setting is enabled and OPTIMIZE query didn't actually assign a merge then
     * an explanatory exception is thrown
     */
    private Boolean optimizeThrowIfNoop;

    /**
     * Try using an index if there is a subquery or a table expression on the right
     * side of the IN operator.
     */
    private Boolean useIndexForInWithSubqueries;

    /**
     * Return empty result when aggregating without keys on empty set.
     */
    private Boolean emptyResultForAggregationByEmptySet;

    /**
     * If it is set to true, then a user is allowed to executed distributed DDL
     * queries.
     */
    private Boolean allowDistributedDdl;

    /**
     * Max size of filed can be read from ODBC dictionary. Long strings are
     * truncated.
     */
    private Integer odbcMaxFieldSize;

    /**
     * Limit on read rows from the most 'deep' sources. That is, only in the deepest
     * subquery. When reading from a remote server, it is only checked on a remote
     * server.
     */
    private Integer maxRowsToRead;

    /**
     * Limit on read bytes (after decompression) from the most 'deep' sources. That
     * is, only in the deepest subquery. When reading from a remote server, it is
     * only checked on a remote server.
     */
    private Integer maxBytesToRead;

    /**
     * Limit on result size in rows. Also checked for intermediate data sent from
     * remote servers.
     */
    private Integer maxResultRows;

    /**
     * Limit on result size in bytes (uncompressed). Also checked for intermediate
     * data sent from remote servers.
     */
    private Integer maxResultBytes;

    /**
     * What to do when the limit is exceeded.
     */
    private String resultOverflowMode;

    /**
     * In rows per second.
     */
    private Integer minExecutionSpeed;

    /**
     * Check that the speed is not too low after the specified time has elapsed.
     */
    private Duration timeoutBeforeCheckingExecutionSpeed;

    /**
     * Maximum depth of query syntax tree. Checked after parsing.
     */
    private Integer maxAstDepth;

    /**
     * Maximum size of query syntax tree in number of nodes. Checked after parsing.
     */
    private Integer maxAstElements;

    /**
     * Maximum size of query syntax tree in number of nodes after expansion of
     * aliases and the asterisk.
     */
    private Integer maxExpandedAstElements;

    /**
     * 0 - everything is allowed. 1 - only read requests. 2 - only read requests, as
     * well as changing settings, except for the 'readonly' setting.
     */
    private Integer readonly;

    /**
     * Maximum size of the set (in number of elements) resulting from the execution
     * of the IN section.
     */
    private Integer maxRowsInSet;

    /**
     * Maximum size of the set (in bytes in memory) resulting from the execution of
     * the IN section.
     */
    private Integer maxBytesInSet;

    /**
     * Maximum size of the hash table for JOIN (in number of rows).
     */
    private Integer maxRowsInJoin;

    /**
     * Maximum size of the hash table for JOIN (in number of bytes in memory).
     */
    private Integer maxBytesInJoin;

    /**
     * Maximum size (in rows) of the transmitted external table obtained when the
     * GLOBAL IN/JOIN section is executed.
     */
    private Integer maxRowsToTransfer;

    /**
     * Maximum size (in uncompressed bytes) of the transmitted external table
     * obtained when the GLOBAL IN/JOIN section is executed.
     */
    private Integer maxBytesToTransfer;

    /**
     * Maximum number of elements during execution of DISTINCT.
     */
    private Integer maxRowsInDistinct;

    /**
     * Maximum total size of state (in uncompressed bytes) in memory for the
     * execution of DISTINCT.
     */
    private Integer maxBytesInDistinct;

    /**
     * Maximum memory usage for processing of single query. Zero means unlimited.
     */
    private Integer maxMemoryUsage;

    /**
     * Maximum memory usage for processing all concurrently running queries for the
     * user. Zero means unlimited.
     */
    private Integer maxMemoryUsageForUser;

    /**
     * Maximum memory usage for processing all concurrently running queries on the
     * server. Zero means unlimited.
     */
    private Integer maxMemoryUsageForAllQueries;

    /**
     * The maximum speed of data exchange over the network in bytes per second for a
     * query. Zero means unlimited.
     */
    private Integer maxNetworkBandwidth;

    /**
     * The maximum number of bytes (compressed) to receive or transmit over the
     * network for execution of the query.
     */
    private Integer maxNetworkBytes;

    /**
     * The maximum speed of data exchange over the network in bytes per second for
     * all concurrently running user queries. Zero means unlimited.
     */
    private Integer maxNetworkBandwidthForUser;

    /**
     * The maximum speed of data exchange over the network in bytes per second for
     * all concurrently running queries. Zero means unlimited.
     */
    private Integer maxNetworkBandwidthForAllUsers;

    /**
     * The character to be considered as a delimiter in CSV data. If setting with a
     * string, a string has to have a length of 1.
     */
    private Character formatCsvDelimiter;

    /**
     * Enable conditional computations
     */
    private Integer enableConditionalComputation;

    /**
     * Allow Int128, Int256, UInt256 and Decimal256 types
     */
    private Integer allowExperimentalBigintTypes;

    /**
     * charset for converting between Bytes and String
     */
    private String charset = StandardCharsets.UTF_8.name();

    private Integer port;
    private String user;
    private String host;
    private String database;
    private String password;
    private Boolean tcpKeepAlive;
    private Duration queryTimeout;

    private final Map<String, Object> additionalSettings = new HashMap<>();

    // <editor-fold desc="GetterSetters">
    public void setMinCompressBlockSize(Integer minCompressBlockSize) {
        this.minCompressBlockSize = minCompressBlockSize;
    }

    public void setMaxCompressBlockSize(SettingKey maxCompressBlockSize) {
        this.maxCompressBlockSize = maxCompressBlockSize;
    }

    public void setMaxBlockSize(Integer maxBlockSize) {
        this.maxBlockSize = maxBlockSize;
    }

    public void setMaxInsertBlockSize(Integer maxInsertBlockSize) {
        this.maxInsertBlockSize = maxInsertBlockSize;
    }

    public void setMinInsertBlockSizeRows(Integer minInsertBlockSizeRows) {
        this.minInsertBlockSizeRows = minInsertBlockSizeRows;
    }

    public void setMinInsertBlockSizeBytes(Integer minInsertBlockSizeBytes) {
        this.minInsertBlockSizeBytes = minInsertBlockSizeBytes;
    }

    public void setMaxReadBufferSize(Integer maxReadBufferSize) {
        this.maxReadBufferSize = maxReadBufferSize;
    }

    public void setMaxDistributedConnections(Integer maxDistributedConnections) {
        this.maxDistributedConnections = maxDistributedConnections;
    }

    public void setMaxQuerySize(Integer maxQuerySize) {
        this.maxQuerySize = maxQuerySize;
    }

    public void setInteractiveDelay(Integer interactiveDelay) {
        this.interactiveDelay = interactiveDelay;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnectTimeoutWithFailoverMs(Duration connectTimeoutWithFailoverMs) {
        this.connectTimeoutWithFailoverMs = connectTimeoutWithFailoverMs;
    }

    public void setQueueMaxWaitMs(Duration queueMaxWaitMs) {
        this.queueMaxWaitMs = queueMaxWaitMs;
    }

    public void setPollInterval(Integer pollInterval) {
        this.pollInterval = pollInterval;
    }

    public void setDistributedConnectionsPoolSize(Integer distributedConnectionsPoolSize) {
        this.distributedConnectionsPoolSize = distributedConnectionsPoolSize;
    }

    public void setConnectionsWithFailoverMaxTries(Integer connectionsWithFailoverMaxTries) {
        this.connectionsWithFailoverMaxTries = connectionsWithFailoverMaxTries;
    }

    public void setExtremes(Boolean extremes) {
        this.extremes = extremes;
    }

    public void setUseUncompressedCache(Boolean useUncompressedCache) {
        this.useUncompressedCache = useUncompressedCache;
    }

    public void setReplaceRunningQuery(Boolean replaceRunningQuery) {
        this.replaceRunningQuery = replaceRunningQuery;
    }

    public void setBackgroundPoolSize(Integer backgroundPoolSize) {
        this.backgroundPoolSize = backgroundPoolSize;
    }

    public void setBackgroundSchedulePoolSize(Integer backgroundSchedulePoolSize) {
        this.backgroundSchedulePoolSize = backgroundSchedulePoolSize;
    }

    public void setDistributedDirectoryMonitorSleepTimeMs(Duration distributedDirectoryMonitorSleepTimeMs) {
        this.distributedDirectoryMonitorSleepTimeMs = distributedDirectoryMonitorSleepTimeMs;
    }

    public void setDistributedDirectoryMonitorBatchInserts(Boolean distributedDirectoryMonitorBatchInserts) {
        this.distributedDirectoryMonitorBatchInserts = distributedDirectoryMonitorBatchInserts;
    }

    public void setOptimizeMoveToPrewhere(Boolean optimizeMoveToPrewhere) {
        this.optimizeMoveToPrewhere = optimizeMoveToPrewhere;
    }

    public void setReplicationAlterPartitionsSync(Integer replicationAlterPartitionsSync) {
        this.replicationAlterPartitionsSync = replicationAlterPartitionsSync;
    }

    public void setReplicationAlterColumnsTimeout(Integer replicationAlterColumnsTimeout) {
        this.replicationAlterColumnsTimeout = replicationAlterColumnsTimeout;
    }

    public void setTotalsAutoThreshold(Float totalsAutoThreshold) {
        this.totalsAutoThreshold = totalsAutoThreshold;
    }

    public void setCompile(Boolean compile) {
        this.compile = compile;
    }

    public void setCompileExpressions(Boolean compileExpressions) {
        this.compileExpressions = compileExpressions;
    }

    public void setMinCountToCompile(Integer minCountToCompile) {
        this.minCountToCompile = minCountToCompile;
    }

    public void setGroupByTwoLevelThreshold(Integer groupByTwoLevelThreshold) {
        this.groupByTwoLevelThreshold = groupByTwoLevelThreshold;
    }

    public void setGroupByTwoLevelThresholdBytes(Integer groupByTwoLevelThresholdBytes) {
        this.groupByTwoLevelThresholdBytes = groupByTwoLevelThresholdBytes;
    }

    public void setDistributedAggregationMemoryEfficient(Boolean distributedAggregationMemoryEfficient) {
        this.distributedAggregationMemoryEfficient = distributedAggregationMemoryEfficient;
    }

    public void setAggregationMemoryEfficientMergeThreads(Integer aggregationMemoryEfficientMergeThreads) {
        this.aggregationMemoryEfficientMergeThreads = aggregationMemoryEfficientMergeThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void setMaxParallelReplicas(Integer maxParallelReplicas) {
        this.maxParallelReplicas = maxParallelReplicas;
    }

    public void setSkipUnavailableShards(Boolean skipUnavailableShards) {
        this.skipUnavailableShards = skipUnavailableShards;
    }

    public void setDistributedGroupByNoMerge(Boolean distributedGroupByNoMerge) {
        this.distributedGroupByNoMerge = distributedGroupByNoMerge;
    }

    public void setMergeTreeMinRowsForConcurrentRead(Integer mergeTreeMinRowsForConcurrentRead) {
        this.mergeTreeMinRowsForConcurrentRead = mergeTreeMinRowsForConcurrentRead;
    }

    public void setMergeTreeMinRowsForSeek(Integer mergeTreeMinRowsForSeek) {
        this.mergeTreeMinRowsForSeek = mergeTreeMinRowsForSeek;
    }

    public void setMergeTreeCoarseIndexGranularity(Integer mergeTreeCoarseIndexGranularity) {
        this.mergeTreeCoarseIndexGranularity = mergeTreeCoarseIndexGranularity;
    }

    public void setMergeTreeMaxRowsToUseCache(Integer mergeTreeMaxRowsToUseCache) {
        this.mergeTreeMaxRowsToUseCache = mergeTreeMaxRowsToUseCache;
    }

    public void setMergeTreeUniformReadDistribution(Boolean mergeTreeUniformReadDistribution) {
        this.mergeTreeUniformReadDistribution = mergeTreeUniformReadDistribution;
    }

    public void setMysqlMaxRowsToInsert(Integer mysqlMaxRowsToInsert) {
        this.mysqlMaxRowsToInsert = mysqlMaxRowsToInsert;
    }

    public void setOptimizeMinEqualityDisjunctionChainLength(Integer optimizeMinEqualityDisjunctionChainLength) {
        this.optimizeMinEqualityDisjunctionChainLength = optimizeMinEqualityDisjunctionChainLength;
    }

    public void setMinBytesToUseDirectIo(Integer minBytesToUseDirectIo) {
        this.minBytesToUseDirectIo = minBytesToUseDirectIo;
    }

    public void setForceIndexByDate(Boolean forceIndexByDate) {
        this.forceIndexByDate = forceIndexByDate;
    }

    public void setForcePrimaryKey(Boolean forcePrimaryKey) {
        this.forcePrimaryKey = forcePrimaryKey;
    }

    public void setMarkCacheMinLifetime(Integer markCacheMinLifetime) {
        this.markCacheMinLifetime = markCacheMinLifetime;
    }

    public void setMaxStreamsToMaxThreadsRatio(Float maxStreamsToMaxThreadsRatio) {
        this.maxStreamsToMaxThreadsRatio = maxStreamsToMaxThreadsRatio;
    }

    public void setNetworkZstdCompressionLevel(Integer networkZstdCompressionLevel) {
        this.networkZstdCompressionLevel = networkZstdCompressionLevel;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setLogQueries(Boolean logQueries) {
        this.logQueries = logQueries;
    }

    public void setLogQueriesCutToLength(Integer logQueriesCutToLength) {
        this.logQueriesCutToLength = logQueriesCutToLength;
    }

    public void setMaxConcurrentQueriesForUser(Integer maxConcurrentQueriesForUser) {
        this.maxConcurrentQueriesForUser = maxConcurrentQueriesForUser;
    }

    public void setInsertDeduplicate(Boolean insertDeduplicate) {
        this.insertDeduplicate = insertDeduplicate;
    }

    public void setInsertQuorum(Integer insertQuorum) {
        this.insertQuorum = insertQuorum;
    }

    public void setSelectSequentialConsistency(Integer selectSequentialConsistency) {
        this.selectSequentialConsistency = selectSequentialConsistency;
    }

    public void setTableFunctionRemoteMaxAddresses(Integer tableFunctionRemoteMaxAddresses) {
        this.tableFunctionRemoteMaxAddresses = tableFunctionRemoteMaxAddresses;
    }

    public void setReadBackoffMinLatencyMs(Duration readBackoffMinLatencyMs) {
        this.readBackoffMinLatencyMs = readBackoffMinLatencyMs;
    }

    public void setReadBackoffMaxThroughput(Integer readBackoffMaxThroughput) {
        this.readBackoffMaxThroughput = readBackoffMaxThroughput;
    }

    public void setReadBackoffMinIntervalBetweenEventsMs(Duration readBackoffMinIntervalBetweenEventsMs) {
        this.readBackoffMinIntervalBetweenEventsMs = readBackoffMinIntervalBetweenEventsMs;
    }

    public void setReadBackoffMinEvents(Integer readBackoffMinEvents) {
        this.readBackoffMinEvents = readBackoffMinEvents;
    }

    public void setMemoryTrackerFaultProbability(Float memoryTrackerFaultProbability) {
        this.memoryTrackerFaultProbability = memoryTrackerFaultProbability;
    }

    public void setEnableHttpCompression(Boolean enableHttpCompression) {
        this.enableHttpCompression = enableHttpCompression;
    }

    public void setHttpZlibCompressionLevel(Integer httpZlibCompressionLevel) {
        this.httpZlibCompressionLevel = httpZlibCompressionLevel;
    }

    public void setHttpNativeCompressionDisableChecksummingOnDecompress(Boolean httpNativeCompressionDisableChecksummingOnDecompress) {
        this.httpNativeCompressionDisableChecksummingOnDecompress = httpNativeCompressionDisableChecksummingOnDecompress;
    }

    public void setCountDistinctImplementation(String countDistinctImplementation) {
        this.countDistinctImplementation = countDistinctImplementation;
    }

    public void setOutputFormatWriteStatistics(Boolean outputFormatWriteStatistics) {
        this.outputFormatWriteStatistics = outputFormatWriteStatistics;
    }

    public void setAddHttpCorsHeader(Boolean addHttpCorsHeader) {
        this.addHttpCorsHeader = addHttpCorsHeader;
    }

    public void setInputFormatSkipUnknownFields(Boolean inputFormatSkipUnknownFields) {
        this.inputFormatSkipUnknownFields = inputFormatSkipUnknownFields;
    }

    public void setInputFormatValuesInterpretExpressions(Boolean inputFormatValuesInterpretExpressions) {
        this.inputFormatValuesInterpretExpressions = inputFormatValuesInterpretExpressions;
    }

    public void setOutputFormatJsonQuote_64bitIntegers(Boolean outputFormatJsonQuote_64bitIntegers) {
        this.outputFormatJsonQuote_64bitIntegers = outputFormatJsonQuote_64bitIntegers;
    }

    public void setOutputFormatJsonQuoteDenormals(Boolean outputFormatJsonQuoteDenormals) {
        this.outputFormatJsonQuoteDenormals = outputFormatJsonQuoteDenormals;
    }

    public void setOutputFormatPrettyMaxRows(Integer outputFormatPrettyMaxRows) {
        this.outputFormatPrettyMaxRows = outputFormatPrettyMaxRows;
    }

    public void setUseClientTimeZone(Boolean useClientTimeZone) {
        this.useClientTimeZone = useClientTimeZone;
    }

    public void setSendProgressInHttpHeaders(Boolean sendProgressInHttpHeaders) {
        this.sendProgressInHttpHeaders = sendProgressInHttpHeaders;
    }

    public void setHttpHeadersProgressIntervalMs(Integer httpHeadersProgressIntervalMs) {
        this.httpHeadersProgressIntervalMs = httpHeadersProgressIntervalMs;
    }

    public void setFsyncMetadata(Boolean fsyncMetadata) {
        this.fsyncMetadata = fsyncMetadata;
    }

    public void setInputFormatAllowErrorsNum(Integer inputFormatAllowErrorsNum) {
        this.inputFormatAllowErrorsNum = inputFormatAllowErrorsNum;
    }

    public void setInputFormatAllowErrorsRatio(Float inputFormatAllowErrorsRatio) {
        this.inputFormatAllowErrorsRatio = inputFormatAllowErrorsRatio;
    }

    public void setJoinUseNulls(Boolean joinUseNulls) {
        this.joinUseNulls = joinUseNulls;
    }

    public void setMaxReplicaDelayForDistributedQueries(Integer maxReplicaDelayForDistributedQueries) {
        this.maxReplicaDelayForDistributedQueries = maxReplicaDelayForDistributedQueries;
    }

    public void setFallbackToStaleReplicasForDistributedQueries(Boolean fallbackToStaleReplicasForDistributedQueries) {
        this.fallbackToStaleReplicasForDistributedQueries = fallbackToStaleReplicasForDistributedQueries;
    }

    public void setPreferredMaxColumnInBlockSizeBytes(Integer preferredMaxColumnInBlockSizeBytes) {
        this.preferredMaxColumnInBlockSizeBytes = preferredMaxColumnInBlockSizeBytes;
    }

    public void setInsertDistributedSync(Boolean insertDistributedSync) {
        this.insertDistributedSync = insertDistributedSync;
    }

    public void setInsertDistributedTimeout(Integer insertDistributedTimeout) {
        this.insertDistributedTimeout = insertDistributedTimeout;
    }

    public void setDistributedDdlTaskTimeout(Integer distributedDdlTaskTimeout) {
        this.distributedDdlTaskTimeout = distributedDdlTaskTimeout;
    }

    public void setStreamFlushIntervalMs(Duration streamFlushIntervalMs) {
        this.streamFlushIntervalMs = streamFlushIntervalMs;
    }

    public void setFormatSchema(String formatSchema) {
        this.formatSchema = formatSchema;
    }

    public void setInsertAllowMaterializedColumns(Boolean insertAllowMaterializedColumns) {
        this.insertAllowMaterializedColumns = insertAllowMaterializedColumns;
    }

    public void setHttpConnectionTimeout(Duration httpConnectionTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    public void setHttpSendTimeout(Duration httpSendTimeout) {
        this.httpSendTimeout = httpSendTimeout;
    }

    public void setHttpReceiveTimeout(Duration httpReceiveTimeout) {
        this.httpReceiveTimeout = httpReceiveTimeout;
    }

    public void setOptimizeThrowIfNoop(Boolean optimizeThrowIfNoop) {
        this.optimizeThrowIfNoop = optimizeThrowIfNoop;
    }

    public void setUseIndexForInWithSubqueries(Boolean useIndexForInWithSubqueries) {
        this.useIndexForInWithSubqueries = useIndexForInWithSubqueries;
    }

    public void setEmptyResultForAggregationByEmptySet(Boolean emptyResultForAggregationByEmptySet) {
        this.emptyResultForAggregationByEmptySet = emptyResultForAggregationByEmptySet;
    }

    public void setAllowDistributedDdl(Boolean allowDistributedDdl) {
        this.allowDistributedDdl = allowDistributedDdl;
    }

    public void setOdbcMaxFieldSize(Integer odbcMaxFieldSize) {
        this.odbcMaxFieldSize = odbcMaxFieldSize;
    }

    public void setMaxRowsToRead(Integer maxRowsToRead) {
        this.maxRowsToRead = maxRowsToRead;
    }

    public void setMaxBytesToRead(Integer maxBytesToRead) {
        this.maxBytesToRead = maxBytesToRead;
    }

    public void setMaxResultRows(Integer maxResultRows) {
        this.maxResultRows = maxResultRows;
    }

    public void setMaxResultBytes(Integer maxResultBytes) {
        this.maxResultBytes = maxResultBytes;
    }

    public void setResultOverflowMode(String resultOverflowMode) {
        this.resultOverflowMode = resultOverflowMode;
    }

    public void setMinExecutionSpeed(Integer minExecutionSpeed) {
        this.minExecutionSpeed = minExecutionSpeed;
    }

    public void setTimeoutBeforeCheckingExecutionSpeed(Duration timeoutBeforeCheckingExecutionSpeed) {
        this.timeoutBeforeCheckingExecutionSpeed = timeoutBeforeCheckingExecutionSpeed;
    }

    public void setMaxAstDepth(Integer maxAstDepth) {
        this.maxAstDepth = maxAstDepth;
    }

    public void setMaxAstElements(Integer maxAstElements) {
        this.maxAstElements = maxAstElements;
    }

    public void setMaxExpandedAstElements(Integer maxExpandedAstElements) {
        this.maxExpandedAstElements = maxExpandedAstElements;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }

    public void setMaxRowsInSet(Integer maxRowsInSet) {
        this.maxRowsInSet = maxRowsInSet;
    }

    public void setMaxBytesInSet(Integer maxBytesInSet) {
        this.maxBytesInSet = maxBytesInSet;
    }

    public void setMaxRowsInJoin(Integer maxRowsInJoin) {
        this.maxRowsInJoin = maxRowsInJoin;
    }

    public void setMaxBytesInJoin(Integer maxBytesInJoin) {
        this.maxBytesInJoin = maxBytesInJoin;
    }

    public void setMaxRowsToTransfer(Integer maxRowsToTransfer) {
        this.maxRowsToTransfer = maxRowsToTransfer;
    }

    public void setMaxBytesToTransfer(Integer maxBytesToTransfer) {
        this.maxBytesToTransfer = maxBytesToTransfer;
    }

    public void setMaxRowsInDistinct(Integer maxRowsInDistinct) {
        this.maxRowsInDistinct = maxRowsInDistinct;
    }

    public void setMaxBytesInDistinct(Integer maxBytesInDistinct) {
        this.maxBytesInDistinct = maxBytesInDistinct;
    }

    public void setMaxMemoryUsage(Integer maxMemoryUsage) {
        this.maxMemoryUsage = maxMemoryUsage;
    }

    public void setMaxMemoryUsageForUser(Integer maxMemoryUsageForUser) {
        this.maxMemoryUsageForUser = maxMemoryUsageForUser;
    }

    public void setMaxMemoryUsageForAllQueries(Integer maxMemoryUsageForAllQueries) {
        this.maxMemoryUsageForAllQueries = maxMemoryUsageForAllQueries;
    }

    public void setMaxNetworkBandwidth(Integer maxNetworkBandwidth) {
        this.maxNetworkBandwidth = maxNetworkBandwidth;
    }

    public void setMaxNetworkBytes(Integer maxNetworkBytes) {
        this.maxNetworkBytes = maxNetworkBytes;
    }

    public void setMaxNetworkBandwidthForUser(Integer maxNetworkBandwidthForUser) {
        this.maxNetworkBandwidthForUser = maxNetworkBandwidthForUser;
    }

    public void setMaxNetworkBandwidthForAllUsers(Integer maxNetworkBandwidthForAllUsers) {
        this.maxNetworkBandwidthForAllUsers = maxNetworkBandwidthForAllUsers;
    }

    public void setFormatCsvDelimiter(Character formatCsvDelimiter) {
        this.formatCsvDelimiter = formatCsvDelimiter;
    }

    public void setEnableConditionalComputation(Integer enableConditionalComputation) {
        this.enableConditionalComputation = enableConditionalComputation;
    }

    public void setAllowExperimentalBigintTypes(Integer allowExperimentalBigintTypes) {
        this.allowExperimentalBigintTypes = allowExperimentalBigintTypes;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTcpKeepAlive(Boolean tcpKeepAlive) {
        this.tcpKeepAlive = tcpKeepAlive;
    }

    public void setQueryTimeout(Duration queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public Integer getMinCompressBlockSize() {
        return minCompressBlockSize;
    }

    public SettingKey getMaxCompressBlockSize() {
        return maxCompressBlockSize;
    }

    public Integer getMaxBlockSize() {
        return maxBlockSize;
    }

    public Integer getMaxInsertBlockSize() {
        return maxInsertBlockSize;
    }

    public Integer getMinInsertBlockSizeRows() {
        return minInsertBlockSizeRows;
    }

    public Integer getMinInsertBlockSizeBytes() {
        return minInsertBlockSizeBytes;
    }

    public Integer getMaxReadBufferSize() {
        return maxReadBufferSize;
    }

    public Integer getMaxDistributedConnections() {
        return maxDistributedConnections;
    }

    public Integer getMaxQuerySize() {
        return maxQuerySize;
    }

    public Integer getInteractiveDelay() {
        return interactiveDelay;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public Duration getConnectTimeoutWithFailoverMs() {
        return connectTimeoutWithFailoverMs;
    }

    public Duration getQueueMaxWaitMs() {
        return queueMaxWaitMs;
    }

    public Integer getPollInterval() {
        return pollInterval;
    }

    public Integer getDistributedConnectionsPoolSize() {
        return distributedConnectionsPoolSize;
    }

    public Integer getConnectionsWithFailoverMaxTries() {
        return connectionsWithFailoverMaxTries;
    }

    public Boolean getExtremes() {
        return extremes;
    }

    public Boolean getUseUncompressedCache() {
        return useUncompressedCache;
    }

    public Boolean getReplaceRunningQuery() {
        return replaceRunningQuery;
    }

    public Integer getBackgroundPoolSize() {
        return backgroundPoolSize;
    }

    public Integer getBackgroundSchedulePoolSize() {
        return backgroundSchedulePoolSize;
    }

    public Duration getDistributedDirectoryMonitorSleepTimeMs() {
        return distributedDirectoryMonitorSleepTimeMs;
    }

    public Boolean getDistributedDirectoryMonitorBatchInserts() {
        return distributedDirectoryMonitorBatchInserts;
    }

    public Boolean getOptimizeMoveToPrewhere() {
        return optimizeMoveToPrewhere;
    }

    public Integer getReplicationAlterPartitionsSync() {
        return replicationAlterPartitionsSync;
    }

    public Integer getReplicationAlterColumnsTimeout() {
        return replicationAlterColumnsTimeout;
    }

    public Float getTotalsAutoThreshold() {
        return totalsAutoThreshold;
    }

    public Boolean getCompile() {
        return compile;
    }

    public Boolean getCompileExpressions() {
        return compileExpressions;
    }

    public Integer getMinCountToCompile() {
        return minCountToCompile;
    }

    public Integer getGroupByTwoLevelThreshold() {
        return groupByTwoLevelThreshold;
    }

    public Integer getGroupByTwoLevelThresholdBytes() {
        return groupByTwoLevelThresholdBytes;
    }

    public Boolean getDistributedAggregationMemoryEfficient() {
        return distributedAggregationMemoryEfficient;
    }

    public Integer getAggregationMemoryEfficientMergeThreads() {
        return aggregationMemoryEfficientMergeThreads;
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public Integer getMaxParallelReplicas() {
        return maxParallelReplicas;
    }

    public Boolean getSkipUnavailableShards() {
        return skipUnavailableShards;
    }

    public Boolean getDistributedGroupByNoMerge() {
        return distributedGroupByNoMerge;
    }

    public Integer getMergeTreeMinRowsForConcurrentRead() {
        return mergeTreeMinRowsForConcurrentRead;
    }

    public Integer getMergeTreeMinRowsForSeek() {
        return mergeTreeMinRowsForSeek;
    }

    public Integer getMergeTreeCoarseIndexGranularity() {
        return mergeTreeCoarseIndexGranularity;
    }

    public Integer getMergeTreeMaxRowsToUseCache() {
        return mergeTreeMaxRowsToUseCache;
    }

    public Boolean getMergeTreeUniformReadDistribution() {
        return mergeTreeUniformReadDistribution;
    }

    public Integer getMysqlMaxRowsToInsert() {
        return mysqlMaxRowsToInsert;
    }

    public Integer getOptimizeMinEqualityDisjunctionChainLength() {
        return optimizeMinEqualityDisjunctionChainLength;
    }

    public Integer getMinBytesToUseDirectIo() {
        return minBytesToUseDirectIo;
    }

    public Boolean getForceIndexByDate() {
        return forceIndexByDate;
    }

    public Boolean getForcePrimaryKey() {
        return forcePrimaryKey;
    }

    public Integer getMarkCacheMinLifetime() {
        return markCacheMinLifetime;
    }

    public Float getMaxStreamsToMaxThreadsRatio() {
        return maxStreamsToMaxThreadsRatio;
    }

    public Integer getNetworkZstdCompressionLevel() {
        return networkZstdCompressionLevel;
    }

    public Integer getPriority() {
        return priority;
    }

    public Boolean getLogQueries() {
        return logQueries;
    }

    public Integer getLogQueriesCutToLength() {
        return logQueriesCutToLength;
    }

    public Integer getMaxConcurrentQueriesForUser() {
        return maxConcurrentQueriesForUser;
    }

    public Boolean getInsertDeduplicate() {
        return insertDeduplicate;
    }

    public Integer getInsertQuorum() {
        return insertQuorum;
    }

    public Integer getSelectSequentialConsistency() {
        return selectSequentialConsistency;
    }

    public Integer getTableFunctionRemoteMaxAddresses() {
        return tableFunctionRemoteMaxAddresses;
    }

    public Duration getReadBackoffMinLatencyMs() {
        return readBackoffMinLatencyMs;
    }

    public Integer getReadBackoffMaxThroughput() {
        return readBackoffMaxThroughput;
    }

    public Duration getReadBackoffMinIntervalBetweenEventsMs() {
        return readBackoffMinIntervalBetweenEventsMs;
    }

    public Integer getReadBackoffMinEvents() {
        return readBackoffMinEvents;
    }

    public Float getMemoryTrackerFaultProbability() {
        return memoryTrackerFaultProbability;
    }

    public Boolean getEnableHttpCompression() {
        return enableHttpCompression;
    }

    public Integer getHttpZlibCompressionLevel() {
        return httpZlibCompressionLevel;
    }

    public Boolean getHttpNativeCompressionDisableChecksummingOnDecompress() {
        return httpNativeCompressionDisableChecksummingOnDecompress;
    }

    public String getCountDistinctImplementation() {
        return countDistinctImplementation;
    }

    public Boolean getOutputFormatWriteStatistics() {
        return outputFormatWriteStatistics;
    }

    public Boolean getAddHttpCorsHeader() {
        return addHttpCorsHeader;
    }

    public Boolean getInputFormatSkipUnknownFields() {
        return inputFormatSkipUnknownFields;
    }

    public Boolean getInputFormatValuesInterpretExpressions() {
        return inputFormatValuesInterpretExpressions;
    }

    public Boolean getOutputFormatJsonQuote_64bitIntegers() {
        return outputFormatJsonQuote_64bitIntegers;
    }

    public Boolean getOutputFormatJsonQuoteDenormals() {
        return outputFormatJsonQuoteDenormals;
    }

    public Integer getOutputFormatPrettyMaxRows() {
        return outputFormatPrettyMaxRows;
    }

    public Boolean getUseClientTimeZone() {
        return useClientTimeZone;
    }

    public Boolean getSendProgressInHttpHeaders() {
        return sendProgressInHttpHeaders;
    }

    public Integer getHttpHeadersProgressIntervalMs() {
        return httpHeadersProgressIntervalMs;
    }

    public Boolean getFsyncMetadata() {
        return fsyncMetadata;
    }

    public Integer getInputFormatAllowErrorsNum() {
        return inputFormatAllowErrorsNum;
    }

    public Float getInputFormatAllowErrorsRatio() {
        return inputFormatAllowErrorsRatio;
    }

    public Boolean getJoinUseNulls() {
        return joinUseNulls;
    }

    public Integer getMaxReplicaDelayForDistributedQueries() {
        return maxReplicaDelayForDistributedQueries;
    }

    public Boolean getFallbackToStaleReplicasForDistributedQueries() {
        return fallbackToStaleReplicasForDistributedQueries;
    }

    public Integer getPreferredMaxColumnInBlockSizeBytes() {
        return preferredMaxColumnInBlockSizeBytes;
    }

    public Boolean getInsertDistributedSync() {
        return insertDistributedSync;
    }

    public Integer getInsertDistributedTimeout() {
        return insertDistributedTimeout;
    }

    public Integer getDistributedDdlTaskTimeout() {
        return distributedDdlTaskTimeout;
    }

    public Duration getStreamFlushIntervalMs() {
        return streamFlushIntervalMs;
    }

    public String getFormatSchema() {
        return formatSchema;
    }

    public Boolean getInsertAllowMaterializedColumns() {
        return insertAllowMaterializedColumns;
    }

    public Duration getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    public Duration getHttpSendTimeout() {
        return httpSendTimeout;
    }

    public Duration getHttpReceiveTimeout() {
        return httpReceiveTimeout;
    }

    public Boolean getOptimizeThrowIfNoop() {
        return optimizeThrowIfNoop;
    }

    public Boolean getUseIndexForInWithSubqueries() {
        return useIndexForInWithSubqueries;
    }

    public Boolean getEmptyResultForAggregationByEmptySet() {
        return emptyResultForAggregationByEmptySet;
    }

    public Boolean getAllowDistributedDdl() {
        return allowDistributedDdl;
    }

    public Integer getOdbcMaxFieldSize() {
        return odbcMaxFieldSize;
    }

    public Integer getMaxRowsToRead() {
        return maxRowsToRead;
    }

    public Integer getMaxBytesToRead() {
        return maxBytesToRead;
    }

    public Integer getMaxResultRows() {
        return maxResultRows;
    }

    public Integer getMaxResultBytes() {
        return maxResultBytes;
    }

    public String getResultOverflowMode() {
        return resultOverflowMode;
    }

    public Integer getMinExecutionSpeed() {
        return minExecutionSpeed;
    }

    public Duration getTimeoutBeforeCheckingExecutionSpeed() {
        return timeoutBeforeCheckingExecutionSpeed;
    }

    public Integer getMaxAstDepth() {
        return maxAstDepth;
    }

    public Integer getMaxAstElements() {
        return maxAstElements;
    }

    public Integer getMaxExpandedAstElements() {
        return maxExpandedAstElements;
    }

    public Integer getReadonly() {
        return readonly;
    }

    public Integer getMaxRowsInSet() {
        return maxRowsInSet;
    }

    public Integer getMaxBytesInSet() {
        return maxBytesInSet;
    }

    public Integer getMaxRowsInJoin() {
        return maxRowsInJoin;
    }

    public Integer getMaxBytesInJoin() {
        return maxBytesInJoin;
    }

    public Integer getMaxRowsToTransfer() {
        return maxRowsToTransfer;
    }

    public Integer getMaxBytesToTransfer() {
        return maxBytesToTransfer;
    }

    public Integer getMaxRowsInDistinct() {
        return maxRowsInDistinct;
    }

    public Integer getMaxBytesInDistinct() {
        return maxBytesInDistinct;
    }

    public Integer getMaxMemoryUsage() {
        return maxMemoryUsage;
    }

    public Integer getMaxMemoryUsageForUser() {
        return maxMemoryUsageForUser;
    }

    public Integer getMaxMemoryUsageForAllQueries() {
        return maxMemoryUsageForAllQueries;
    }

    public Integer getMaxNetworkBandwidth() {
        return maxNetworkBandwidth;
    }

    public Integer getMaxNetworkBytes() {
        return maxNetworkBytes;
    }

    public Integer getMaxNetworkBandwidthForUser() {
        return maxNetworkBandwidthForUser;
    }

    public Integer getMaxNetworkBandwidthForAllUsers() {
        return maxNetworkBandwidthForAllUsers;
    }

    public Character getFormatCsvDelimiter() {
        return formatCsvDelimiter;
    }

    public Integer getEnableConditionalComputation() {
        return enableConditionalComputation;
    }

    public Integer getAllowExperimentalBigintTypes() {
        return allowExperimentalBigintTypes;
    }

    public String getCharset() {
        return charset;
    }

    public Integer getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getTcpKeepAlive() {
        return tcpKeepAlive;
    }

    public Duration getQueryTimeout() {
        return queryTimeout;
    }
    // </editor-fold>

    public Map<String, Object> getAdditionalSettings() {
        return additionalSettings;
    }

    public void addSettings(SettingKey key, Object value) {
        addSettings(key.name(), value);
    }

    public void addSettings(String key, Object value) {
        if (value != null)
            this.additionalSettings.put(key, value);
    }

    public void withSettings(Map<SettingKey, Object> settings) {
        settings.forEach(this::addSettings);
    }

    public void withProperties(Properties properties) {
        properties.forEach((k, v) -> addSettings(String.valueOf(k), v));
    }

    public Properties asProperties() {
        final Properties properties = new Properties();
        asSettings().forEach((k, v) -> {
            if (v instanceof Duration) {
                if (k.type().equals(SettingType.Seconds)) {
                    properties.put(k.name(), ((Duration) v).toSeconds());
                } else {
                    properties.put(k.name(), ((Duration) v).toMillis());
                }
            } else {
                properties.put(k.name(), v);
            }
        });
        return properties;
    }

    public Map<SettingKey, Object> asSettings() {
        final Map<SettingKey, Object> settings = new HashMap<>();

        setSetting(settings, SettingKey.min_compress_block_size, minCompressBlockSize);
        setSetting(settings, SettingKey.max_compress_block_size, maxCompressBlockSize);
        setSetting(settings, SettingKey.max_block_size, maxBlockSize);
        setSetting(settings, SettingKey.max_insert_block_size, maxInsertBlockSize);
        setSetting(settings, SettingKey.min_insert_block_size_rows, minInsertBlockSizeRows);
        setSetting(settings, SettingKey.min_insert_block_size_bytes, minInsertBlockSizeBytes);
        setSetting(settings, SettingKey.max_read_buffer_size, maxReadBufferSize);
        setSetting(settings, SettingKey.max_distributed_connections, maxDistributedConnections);
        setSetting(settings, SettingKey.max_query_size, maxQuerySize);
        setSetting(settings, SettingKey.interactive_delay, interactiveDelay);
        setSetting(settings, SettingKey.connect_timeout, connectTimeout);
        setSetting(settings, SettingKey.connect_timeout_with_failover_ms, connectTimeoutWithFailoverMs);
        setSetting(settings, SettingKey.queue_max_wait_ms, queueMaxWaitMs);
        setSetting(settings, SettingKey.poll_interval, pollInterval);
        setSetting(settings, SettingKey.distributed_connections_pool_size, distributedConnectionsPoolSize);
        setSetting(settings, SettingKey.connections_with_failover_max_tries, connectionsWithFailoverMaxTries);
        setSetting(settings, SettingKey.extremes, extremes);
        setSetting(settings, SettingKey.use_uncompressed_cache, useUncompressedCache);
        setSetting(settings, SettingKey.replace_running_query, replaceRunningQuery);
        setSetting(settings, SettingKey.background_pool_size, backgroundPoolSize);
        setSetting(settings, SettingKey.background_schedule_pool_size, backgroundSchedulePoolSize);
        setSetting(settings, SettingKey.distributed_directory_monitor_sleep_time_ms, distributedDirectoryMonitorSleepTimeMs);
        setSetting(settings, SettingKey.distributed_directory_monitor_batch_inserts, distributedDirectoryMonitorBatchInserts);
        setSetting(settings, SettingKey.optimize_move_to_prewhere, optimizeMoveToPrewhere);
        setSetting(settings, SettingKey.replication_alter_partitions_sync, replicationAlterPartitionsSync);
        setSetting(settings, SettingKey.replication_alter_columns_timeout, replicationAlterColumnsTimeout);
        setSetting(settings, SettingKey.totals_auto_threshold, totalsAutoThreshold);
        setSetting(settings, SettingKey.compile, compile);
        setSetting(settings, SettingKey.compile_expressions, compileExpressions);
        setSetting(settings, SettingKey.min_count_to_compile, minCountToCompile);
        setSetting(settings, SettingKey.group_by_two_level_threshold, groupByTwoLevelThreshold);
        setSetting(settings, SettingKey.group_by_two_level_threshold_bytes, groupByTwoLevelThresholdBytes);
        setSetting(settings, SettingKey.distributed_aggregation_memory_efficient, distributedAggregationMemoryEfficient);
        setSetting(settings, SettingKey.aggregation_memory_efficient_merge_threads, aggregationMemoryEfficientMergeThreads);
        setSetting(settings, SettingKey.max_threads, maxThreads);
        setSetting(settings, SettingKey.max_parallel_replicas, maxParallelReplicas);
        setSetting(settings, SettingKey.skip_unavailable_shards, skipUnavailableShards);
        setSetting(settings, SettingKey.distributed_group_by_no_merge, distributedGroupByNoMerge);
        setSetting(settings, SettingKey.merge_tree_min_rows_for_concurrent_read, mergeTreeMinRowsForConcurrentRead);
        setSetting(settings, SettingKey.merge_tree_min_rows_for_seek, mergeTreeMinRowsForSeek);
        setSetting(settings, SettingKey.merge_tree_coarse_index_granularity, mergeTreeCoarseIndexGranularity);
        setSetting(settings, SettingKey.merge_tree_max_rows_to_use_cache, mergeTreeMaxRowsToUseCache);
        setSetting(settings, SettingKey.merge_tree_uniform_read_distribution, mergeTreeUniformReadDistribution);
        setSetting(settings, SettingKey.mysql_max_rows_to_insert, mysqlMaxRowsToInsert);
        setSetting(settings, SettingKey.optimize_min_equality_disjunction_chain_length, optimizeMinEqualityDisjunctionChainLength);
        setSetting(settings, SettingKey.min_bytes_to_use_direct_io, minBytesToUseDirectIo);
        setSetting(settings, SettingKey.force_index_by_date, forceIndexByDate);
        setSetting(settings, SettingKey.force_primary_key, forcePrimaryKey);
        setSetting(settings, SettingKey.mark_cache_min_lifetime, markCacheMinLifetime);
        setSetting(settings, SettingKey.max_streams_to_max_threads_ratio, maxStreamsToMaxThreadsRatio);
        setSetting(settings, SettingKey.network_zstd_compression_level, networkZstdCompressionLevel);
        setSetting(settings, SettingKey.priority, priority);
        setSetting(settings, SettingKey.log_queries, logQueries);
        setSetting(settings, SettingKey.log_queries_cut_to_length, logQueriesCutToLength);
        setSetting(settings, SettingKey.max_concurrent_queries_for_user, maxConcurrentQueriesForUser);
        setSetting(settings, SettingKey.insert_deduplicate, insertDeduplicate);
        setSetting(settings, SettingKey.insert_quorum, insertQuorum);
        setSetting(settings, SettingKey.select_sequential_consistency, selectSequentialConsistency);
        setSetting(settings, SettingKey.table_function_remote_max_addresses, tableFunctionRemoteMaxAddresses);
        setSetting(settings, SettingKey.read_backoff_min_latency_ms, readBackoffMinLatencyMs);
        setSetting(settings, SettingKey.read_backoff_max_throughput, readBackoffMaxThroughput);
        setSetting(settings, SettingKey.read_backoff_min_interval_between_events_ms, readBackoffMinIntervalBetweenEventsMs);
        setSetting(settings, SettingKey.read_backoff_min_events, readBackoffMinEvents);
        setSetting(settings, SettingKey.memory_tracker_fault_probability, memoryTrackerFaultProbability);
        setSetting(settings, SettingKey.enable_http_compression, enableHttpCompression);
        setSetting(settings, SettingKey.http_zlib_compression_level, httpZlibCompressionLevel);
        setSetting(settings, SettingKey.http_native_compression_disable_checksumming_on_decompress,
                httpNativeCompressionDisableChecksummingOnDecompress);
        setSetting(settings, SettingKey.count_distinct_implementation, countDistinctImplementation);
        setSetting(settings, SettingKey.output_format_write_statistics, outputFormatWriteStatistics);
        setSetting(settings, SettingKey.add_http_cors_header, addHttpCorsHeader);
        setSetting(settings, SettingKey.input_format_skip_unknown_fields, inputFormatSkipUnknownFields);
        setSetting(settings, SettingKey.input_format_values_interpret_expressions, inputFormatValuesInterpretExpressions);
        setSetting(settings, SettingKey.output_format_json_quote_64bit_integers, outputFormatJsonQuote_64bitIntegers);
        setSetting(settings, SettingKey.output_format_json_quote_denormals, outputFormatJsonQuoteDenormals);
        setSetting(settings, SettingKey.output_format_pretty_max_rows, outputFormatPrettyMaxRows);
        setSetting(settings, SettingKey.use_client_time_zone, useClientTimeZone);
        setSetting(settings, SettingKey.send_progress_in_http_headers, sendProgressInHttpHeaders);
        setSetting(settings, SettingKey.http_headers_progress_interval_ms, httpHeadersProgressIntervalMs);
        setSetting(settings, SettingKey.fsync_metadata, fsyncMetadata);
        setSetting(settings, SettingKey.input_format_allow_errors_num, inputFormatAllowErrorsNum);
        setSetting(settings, SettingKey.input_format_allow_errors_ratio, inputFormatAllowErrorsRatio);
        setSetting(settings, SettingKey.join_use_nulls, joinUseNulls);
        setSetting(settings, SettingKey.max_replica_delay_for_distributed_queries, maxReplicaDelayForDistributedQueries);
        setSetting(settings, SettingKey.fallback_to_stale_replicas_for_distributed_queries, fallbackToStaleReplicasForDistributedQueries);
        setSetting(settings, SettingKey.preferred_max_column_in_block_size_bytes, preferredMaxColumnInBlockSizeBytes);
        setSetting(settings, SettingKey.insert_distributed_sync, insertDistributedSync);
        setSetting(settings, SettingKey.insert_distributed_timeout, insertDistributedTimeout);
        setSetting(settings, SettingKey.distributed_ddl_task_timeout, distributedDdlTaskTimeout);
        setSetting(settings, SettingKey.stream_flush_interval_ms, streamFlushIntervalMs);
        setSetting(settings, SettingKey.format_schema, formatSchema);
        setSetting(settings, SettingKey.insert_allow_materialized_columns, insertAllowMaterializedColumns);
        setSetting(settings, SettingKey.http_connection_timeout, httpConnectionTimeout);
        setSetting(settings, SettingKey.http_send_timeout, httpSendTimeout);
        setSetting(settings, SettingKey.http_receive_timeout, httpReceiveTimeout);
        setSetting(settings, SettingKey.optimize_throw_if_noop, optimizeThrowIfNoop);
        setSetting(settings, SettingKey.use_index_for_in_with_subqueries, useIndexForInWithSubqueries);
        setSetting(settings, SettingKey.empty_result_for_aggregation_by_empty_set, emptyResultForAggregationByEmptySet);
        setSetting(settings, SettingKey.allow_distributed_ddl, allowDistributedDdl);
        setSetting(settings, SettingKey.odbc_max_field_size, odbcMaxFieldSize);
        setSetting(settings, SettingKey.max_rows_to_read, maxRowsToRead);
        setSetting(settings, SettingKey.max_bytes_to_read, maxBytesToRead);
        setSetting(settings, SettingKey.max_result_rows, maxResultRows);
        setSetting(settings, SettingKey.max_result_bytes, maxResultBytes);
        setSetting(settings, SettingKey.result_overflow_mode, resultOverflowMode);
        setSetting(settings, SettingKey.min_execution_speed, minExecutionSpeed);
        setSetting(settings, SettingKey.timeout_before_checking_execution_speed, timeoutBeforeCheckingExecutionSpeed);
        setSetting(settings, SettingKey.max_ast_depth, maxAstDepth);
        setSetting(settings, SettingKey.max_ast_elements, maxAstElements);
        setSetting(settings, SettingKey.max_expanded_ast_elements, maxExpandedAstElements);
        setSetting(settings, SettingKey.readonly, readonly);
        setSetting(settings, SettingKey.max_rows_in_set, maxRowsInSet);
        setSetting(settings, SettingKey.max_bytes_in_set, maxBytesInSet);
        setSetting(settings, SettingKey.max_rows_in_join, maxRowsInJoin);
        setSetting(settings, SettingKey.max_bytes_in_join, maxBytesInJoin);
        setSetting(settings, SettingKey.max_rows_to_transfer, maxRowsToTransfer);
        setSetting(settings, SettingKey.max_bytes_to_transfer, maxBytesToTransfer);
        setSetting(settings, SettingKey.max_rows_in_distinct, maxRowsInDistinct);
        setSetting(settings, SettingKey.max_bytes_in_distinct, maxBytesInDistinct);
        setSetting(settings, SettingKey.max_memory_usage, maxMemoryUsage);
        setSetting(settings, SettingKey.max_memory_usage_for_user, maxMemoryUsageForUser);
        setSetting(settings, SettingKey.max_memory_usage_for_all_queries, maxMemoryUsageForAllQueries);
        setSetting(settings, SettingKey.max_network_bandwidth, maxNetworkBandwidth);
        setSetting(settings, SettingKey.max_network_bytes, maxNetworkBytes);
        setSetting(settings, SettingKey.max_network_bandwidth_for_user, maxNetworkBandwidthForUser);
        setSetting(settings, SettingKey.max_network_bandwidth_for_all_users, maxNetworkBandwidthForAllUsers);
        setSetting(settings, SettingKey.format_csv_delimiter, formatCsvDelimiter);
        setSetting(settings, SettingKey.enable_conditional_computation, enableConditionalComputation);
        setSetting(settings, SettingKey.allow_experimental_bigint_types, allowExperimentalBigintTypes);
        setSetting(settings, SettingKey.charset, charset);
        setSetting(settings, SettingKey.port, port);
        setSetting(settings, SettingKey.user, user);
        setSetting(settings, SettingKey.host, host);
        setSetting(settings, SettingKey.database, database);
        setSetting(settings, SettingKey.password, password);
        setSetting(settings, SettingKey.tcp_keep_alive, tcpKeepAlive);
        setSetting(settings, SettingKey.query_timeout, queryTimeout);

        additionalSettings.entrySet().stream()
                .filter(e -> SettingKey.definedSettingKeys().containsKey(e.getKey()))
                .forEach(e -> {
                    final SettingKey key = SettingKey.definedSettingKeys().get(e.getKey());
                    setSetting(settings, key, e.getValue());
                });

        setSettingAsDuration(settings, SettingKey.query_timeout);
        setSettingAsDuration(settings, SettingKey.connect_timeout);

        return settings;
    }

    private void setSettingAsDuration(Map<SettingKey, Object> settings, SettingKey key) {
        settings.computeIfPresent(key, (k, v) -> {
            v = (v instanceof Integer) ? ((Integer) v).longValue() : v;
            if (v instanceof Long) {
                if (k.type().equals(SettingType.Milliseconds)) {
                    return Duration.ofMillis(((Long) v));
                } else {
                    return Duration.ofSeconds(((Long) v));
                }
            }

            return v;
        });
    }

    private void setSetting(Map<SettingKey, Object> settings, SettingKey key, Object value) {
        if (value != null) {
            if (value instanceof Duration) {
                settings.put(key, ((Duration) value).toMillis());
            } else {
                settings.put(key, value);
            }
        }
    }
}
