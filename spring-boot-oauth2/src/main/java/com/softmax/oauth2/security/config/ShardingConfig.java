package com.softmax.oauth2.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Jarvis
 * @date 2020/10/22
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sharding")
public class ShardingConfig {

    private final Logger logger = LoggerFactory.getLogger(ShardingConfig.class);
    /**
     * datasource 数据源配置信息
     */
    private Map<String, Object> datasource;

    /**
     * sharding 规则配置信息
     */
    private Map<String, Object> shardingRule;

    public void setDatasource(Map<String, Object> datasource) {
        this.datasource = datasource;
    }

    public void setShardingRule(Map<String, Object> shardingRule) {
        this.shardingRule = shardingRule;
    }

    /**
     * 数据节点Key
     */
    private final static String ACTUAL_DATANODES = "actualDataNodes";
    /**
     * 分库策略Key
     */
    private final static String DATABASE_STRATEGY = "databaseStrategy";

    /**
     * 分表策略Key
     */
    private final static String TABLE_STRATEGY = "tableStrategy";

    /**
     * 最小连接池
     */
    private final static int MIN_POOL_SIZE = 10;

    /**
     * 最大连接池
     */
    private final static int MAX_POOL_SIZE = 60;

    /**
     * 初始化最小连接池
     */
    private final static int INIT_SIZE = 5;

    /**
     * 最大等待时间
     */
    private final static int MAX_WAIT = 1800000;

    private final static int CONNECTION_TIMEOUT = 30000;

    private final static int IDLE_TIMEOUT = 30000;

    /**
     * 获取数据源对象
     *
     * @throws SQLException
     */
    @Bean
    public DataSource shardingCreateDataSource() throws SQLException, ConfigurationException {
        Map<String, DataSource> dataSourceMap = getDataSourceMap();
        ShardingRuleConfiguration shardingRuleConfig = getShardingRuleConfig();
        Properties properties = new Properties();
        properties.put("sql.show", true);
        // 获取数据源对象
        DataSource dataSource = null;
        try {
            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("------------------- The datasource has been created ---------------------------");
        return dataSource;
    }

    /**
     * 配置数据源配置
     *
     * @return
     */
    private Map<String, DataSource> getDataSourceMap() throws ConfigurationException, SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);
        if (datasource.isEmpty()) {
            throw new ConfigurationException("Data source configuration error");
        }

        Properties properties = new Properties();
        properties.setProperty("testOnBorrow", "false");
        properties.setProperty("testOnReturn", "false");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("validationQuery", "select 'x'");

        String[] dsNames;
        try {
            dsNames = datasource.get("names").toString().split(",");
        } catch (Exception e) {
            throw new ConfigurationException("Data source configuration error");
        }

        for (String dsName : dsNames) {
            LinkedHashMap<String, String> dbConfigMap = (LinkedHashMap<String, String>) datasource.get(dsName);
            String driverClassName = dbConfigMap.get("driverClassName");
            String type = dbConfigMap.get("type");
            String dbUrl = dbConfigMap.get("url");
            String dbUsername = dbConfigMap.get("username");
            String dbPassword = dbConfigMap.get("password");
            Class clsDbType;
            try {
                clsDbType = Class.forName(type);
            } catch (ClassNotFoundException e) {
                throw new ConfigurationException("Data source type configuration error");
            }
            Object obj;
            try {
                obj = clsDbType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new ConfigurationException("Data source type configuration error");
            }

            if (obj instanceof HikariDataSource) {
                HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setDriverClassName(driverClassName);
                hikariConfig.setJdbcUrl(dbUrl);
                hikariConfig.setUsername(dbUsername);
                hikariConfig.setPassword(dbPassword);

                //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
                hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT);
                //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
                hikariConfig.setIdleTimeout(IDLE_TIMEOUT);
                //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，
                // 参考MySQL wait_timeout参数（show variables like '%timeout%';）
                hikariConfig.setMaxLifetime(MAX_WAIT);
                //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
                hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);
                hikariConfig.setMinimumIdle(MIN_POOL_SIZE);
                hikariConfig.setDataSourceProperties(properties);

                HikariDataSource dataSource = new HikariDataSource(hikariConfig);
                logger.info("-------------------Init HikariDataSource {} ---------------------------", dsName);
                dataSourceMap.put(dsName, dataSource);
            } else if (obj instanceof DruidDataSource) {
                DruidDataSource druidDataSource = new DruidDataSource();
                druidDataSource.setUsername(dbUsername);
                druidDataSource.setPassword(dbPassword);
                druidDataSource.setUrl(dbUrl);
                druidDataSource.setDriverClassName(driverClassName);

                druidDataSource.setFilters("stat");
                druidDataSource.setInitialSize(INIT_SIZE);
                druidDataSource.setMinIdle(MIN_POOL_SIZE);
                druidDataSource.setMaxActive(MAX_POOL_SIZE);
                // 连接等待超时的时间
                druidDataSource.setMaxWait(MAX_WAIT);
                // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                druidDataSource.setTimeBetweenEvictionRunsMillis(2000);
                //连接在池中最大生存的时间，单位是毫秒
                druidDataSource.setMaxEvictableIdleTimeMillis(900000);
                //连接在池中最小生存的时间，单位是毫秒
                druidDataSource.setMinEvictableIdleTimeMillis(600000);

                druidDataSource.setUseGlobalDataSourceStat(true);
            } else {
                throw new ConfigurationException("Data source  configuration error");
            }
        }
        return dataSourceMap;
    }

    /**
     * Sharding规则配置
     *
     * @return ShardingRuleConfiguration
     */
    private ShardingRuleConfiguration getShardingRuleConfig() throws ConfigurationException {

        if (shardingRule.isEmpty()) {
            throw new ConfigurationException("Sharding rule configuration error");
        }
        // 配置规则
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        //分表规则
        ShardingStrategyInfo shardingStrategyInfo = getTableRuleConfigurations();
        for (TableRuleConfiguration tableRuleConfig : shardingStrategyInfo.getTableRuleConfigurations()) {
            shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfig);
        }

        //绑定表规则列表
        shardingRuleConfiguration.getBindingTableGroups().add(shardingStrategyInfo.getBindingTableGroups());

        //分片规则,配置 t_order 被拆分到多个子库的策略
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration(shardingStrategyInfo.getDatabaseShardingColumn(),
                        new PreciseModuloShardingDatabaseAlgorithm()));

        //分表规则 t_order 被拆分到多个子表的策略
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration(shardingStrategyInfo.getTableShardingColumn(),
                        new PreciseModuloShardingTableAlgorithm()));

        //读写分离规则
        List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = getMasterSlaveRules();
        shardingRuleConfiguration.setMasterSlaveRuleConfigs(masterSlaveRuleConfigurations);

        return shardingRuleConfiguration;
    }

    /**
     * 分片（分库分表）规则
     *
     * @return
     */
    private ShardingStrategyInfo getTableRuleConfigurations() throws ConfigurationException {
        LinkedHashMap<String, Object> ruleConfigList = (LinkedHashMap<String, Object>) shardingRule.get("tables");
        if (ruleConfigList.isEmpty()) {
            throw new ConfigurationException("Table rule configuration error");
        }

        ShardingStrategyInfo shardingStrategyInfo = new ShardingStrategyInfo();
        //分表策略
        List<TableRuleConfiguration> tableRuleConfigurations = new ArrayList<>(4);
        Set<String> tbNames = ruleConfigList.keySet();

        for (String tbName : tbNames) {
            Object actualStrategyConfig = ruleConfigList.get(tbName);
            LinkedHashMap<String, Object> strategyConfigMap = (LinkedHashMap<String, Object>) actualStrategyConfig;
            //数据节点
            String actualDataNodes = strategyConfigMap.get(ACTUAL_DATANODES).toString();
            if (StringUtils.isEmpty(actualDataNodes)) {
                throw new ConfigurationException(ACTUAL_DATANODES + " configuration error");
            }
            //分片策略
            LinkedHashMap<String, LinkedHashMap<String, String>> databaStrategyConfig = (LinkedHashMap<String, LinkedHashMap<String, String>>) strategyConfigMap.get(DATABASE_STRATEGY);
            //算法和列
            LinkedHashMap<String, String> databaseAlgorithmColumn = databaStrategyConfig.get("inline");
            //列名
            String dbShardingColumn = databaseAlgorithmColumn.get("shardingColumn");
            //算法
            String dbAlgorithmExpression = databaseAlgorithmColumn.get("algorithmExpression");

            //分表策略
            LinkedHashMap<String, LinkedHashMap<String, String>> tableStrategyConfig = (LinkedHashMap<String, LinkedHashMap<String, String>>) strategyConfigMap.get(TABLE_STRATEGY);
            //算法和列
            LinkedHashMap<String, String> tableAlgorithmColumn = tableStrategyConfig.get("inline");
            //列名
            String tbShardingColumn = tableAlgorithmColumn.get("shardingColumn");
            //算法
            String tbAlgorithmExpression = tableAlgorithmColumn.get("algorithmExpression");

            //表规则配置信息
            TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(tbName, actualDataNodes);

            //默认自增列值生成器配置，缺省将使用SnowflakeKeyGenerator
            tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", tbShardingColumn));

            //设置分片的列和算法
            if (shardingStrategyInfo.getDatabaseShardingColumn() != null &&
                    shardingStrategyInfo.getDatabaseAlgorithmExpression() != null) {
                shardingStrategyInfo.setDatabaseShardingColumn(dbShardingColumn);
                shardingStrategyInfo.setDatabaseAlgorithmExpression(dbAlgorithmExpression);
            }

            //设置分表的列和算法
            if (shardingStrategyInfo.getTableShardingColumn() != null && shardingStrategyInfo.getTableAlgorithmExpression() != null) {
                shardingStrategyInfo.setTableShardingColumn(tbShardingColumn);
                shardingStrategyInfo.setTableAlgorithmExpression(tbAlgorithmExpression);
            }

            tableRuleConfigurations.add(tableRuleConfiguration);
        }
        String tableGroups = org.apache.commons.lang3.StringUtils.join(tbNames, ",");
        shardingStrategyInfo.setBindingTableGroups(tableGroups);
        shardingStrategyInfo.setTableRuleConfigurations(tableRuleConfigurations);

        return shardingStrategyInfo;
    }


    /**
     * 读写分离规则
     */
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRules() throws ConfigurationException {
        LinkedHashMap<String, Object> ruleConfigList = (LinkedHashMap<String, Object>) shardingRule.get("masterSlaveRules");
        if (ruleConfigList.isEmpty()) {
            throw new ConfigurationException("The read-write separation rule is configured incorrectly or not configured");
        }
        Set<String> instanceNames = ruleConfigList.keySet();
        if (instanceNames.isEmpty()) {
            throw new ConfigurationException("Misconfiguration of read-write separation rules");
        }
        List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = new ArrayList<>(4);
        for (String name : instanceNames) {
            LinkedHashMap<String, Object> masterSlaveRule = (LinkedHashMap<String, Object>) ruleConfigList.get(name);
            //master 数据源名称
            String masterDataSourceName = masterSlaveRule.get("masterDataSourceName").toString();
            //负载均衡策略
            String loadBalanceAlgorithm = masterSlaveRule.get("loadBalanceAlgorithmType").toString();
            //slave 节点
            LinkedHashMap<String, Object> slaveDataSourceMap = (LinkedHashMap<String, Object>) masterSlaveRule.get("slaveDataSourceNames");
            String[] slaveDataSourceNames = slaveDataSourceMap.values().toArray(new String[0]);
            MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(name,
                    masterDataSourceName,
                    Arrays.asList(slaveDataSourceNames),
                    new LoadBalanceStrategyConfiguration(loadBalanceAlgorithm));

            masterSlaveRuleConfigurations.add(masterSlaveRuleConfig);
        }
        return masterSlaveRuleConfigurations;
    }

}

