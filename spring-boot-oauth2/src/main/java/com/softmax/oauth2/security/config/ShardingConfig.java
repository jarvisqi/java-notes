package com.softmax.oauth2.security.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
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
import java.sql.SQLException;
import java.util.*;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sharding")
public class ShardingConfig {

    private final Logger logger = LoggerFactory.getLogger(ShardingConfig.class);

    /**
     * 数据节点
     */
    private final static String ACTUAL_DATANODES = "actualDataNodes";
    /**
     * 分库策略
     */
    private final static String DATABASE_STRATEGY = "databaseStrategy";

    /**
     * 分表策略
     */
    private final static String TABLE_STRATEGY = "tableStrategy";

    /**
     * 数据源所有配置
     */
    private Map<String, Object> datasource;

    /**
     * 规则
     */
    private Map<String, Object> shardingRule;

    public void setDatasource(Map<String, Object> datasource) {
        this.datasource = datasource;
    }

    public void setShardingRule(Map<String, Object> shardingRule) {
        this.shardingRule = shardingRule;
    }

    public Map<String, Object> getDatasource() {
        return datasource;
    }

    /**
     * 获取数据源对象
     *
     * @throws SQLException
     */
    @Bean
    public DataSource shardingCreateDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = getDataSourceMap();
        ShardingRuleConfiguration shardingRuleConfig = getShardingRuleConfig();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        return dataSource;
    }

    /**
     * 配置数据源配置
     *
     * @return
     */
    private Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);
        if (datasource.isEmpty()) {
            new ConfigurationException("数据源配置错误");
        }

        Properties properties = new Properties();
        properties.setProperty("testOnBorrow", "true");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("testOnReturn", "false");
        properties.setProperty("validationQuery", "select 'x'");

        for (String ds : datasource.keySet()) {

            LinkedHashMap<String, String> dbConfigList = (LinkedHashMap<String, String>) datasource.get(ds);
            String driverClassName = dbConfigList.get("driverClassName");
            //String type = dbConfigList.get("type");
            String name = dbConfigList.get("name");
            String jdbcUrl = dbConfigList.get("url");
            String dbUsername = dbConfigList.get("username");
            String dbPassword = dbConfigList.get("password");

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(driverClassName);
            hikariConfig.setJdbcUrl(jdbcUrl);
            hikariConfig.setUsername(dbUsername);
            hikariConfig.setPassword(dbPassword);

            //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
            hikariConfig.setConnectionTimeout(30000);
            //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
            hikariConfig.setIdleTimeout(30000);
            //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，
            // 参考MySQL wait_timeout参数（show variables like '%timeout%';）
            hikariConfig.setMaxLifetime(1800000);
            //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
            hikariConfig.setMaximumPoolSize(60);
            hikariConfig.setMinimumIdle(10);
            hikariConfig.setDataSourceProperties(properties);

            HikariDataSource dataSource = new HikariDataSource(hikariConfig);

            dataSourceMap.put(name, dataSource);
        }
        return dataSourceMap;
    }

    /**
     * 解析Sharding规则配置
     *
     * @return ShardingRuleConfiguration
     */
    private ShardingRuleConfiguration getShardingRuleConfig() {

        if (shardingRule.isEmpty()) {
            new ConfigurationException("规则配置错误");
        }
        // 配置规则
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        //分片（分库分表）规则
        List<TableRuleConfiguration> tableRuleConfigurations = getTableRuleConfigurations();
        shardingRuleConfiguration.setTableRuleConfigs(tableRuleConfigurations);
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
    private List<TableRuleConfiguration> getTableRuleConfigurations() {
        LinkedHashMap<String, Object> ruleConfigList = (LinkedHashMap<String, Object>) shardingRule.get("tables");
        if (ruleConfigList.isEmpty()) {
            new ConfigurationException("规则配置错误");
        }
        Set<String> tbNames = ruleConfigList.keySet();
        List<TableRuleConfiguration> tableRuleConfigurations = new ArrayList<>(8);
        for (String tbName : tbNames) {
            Object actualStrategyConfig = ruleConfigList.get(tbName);
            LinkedHashMap<String, Object> strategyConfigMap = (LinkedHashMap<String, Object>) actualStrategyConfig;
            //数据节点
            String actualDataNodes = strategyConfigMap.get(ACTUAL_DATANODES).toString();
            if (!StringUtils.isEmpty(actualDataNodes)) {
                new ConfigurationException(ACTUAL_DATANODES + "数据节点配置错误");
            }

            for (String configKey : strategyConfigMap.keySet()) {
                if (configKey.equals(ACTUAL_DATANODES)) {
                    continue;
                }
                // 配置表规则
                LinkedHashMap<String, Object> inlineConfigMap = (LinkedHashMap<String, Object>) strategyConfigMap.get(configKey);
                //算法和列
                LinkedHashMap<String, String> algorithmColumn = (LinkedHashMap<String, String>) inlineConfigMap.get("inline");
                //列名
                String shardingColumn = algorithmColumn.get("shardingColumn");
                //算法
                String algorithmExpression = algorithmColumn.get("algorithmExpression");
                //规则配置信息
                TableRuleConfiguration ruleConfiguration = new TableRuleConfiguration(tbName, actualDataNodes);
                if (configKey.equals(DATABASE_STRATEGY)) {
                    // 配置分库策略
                    ruleConfiguration.setDatabaseShardingStrategyConfig(
                            new InlineShardingStrategyConfiguration(shardingColumn, algorithmExpression));
                    logger.info("分库策略,使用的列名:{},使用的算法:{}", shardingColumn, algorithmExpression);
                } else if (configKey.equals(TABLE_STRATEGY)) {
                    //配置分表策略
                    ruleConfiguration.setTableShardingStrategyConfig(
                            new InlineShardingStrategyConfiguration(shardingColumn, algorithmExpression));
                    logger.info("分表策略,使用的列名{},使用的算法{}", shardingColumn, algorithmExpression);
                }
                tableRuleConfigurations.add(ruleConfiguration);
            }
        }

        return tableRuleConfigurations;
    }


    /**
     * 读写分离规则
     */
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRules() {
        LinkedHashMap<String, Object> ruleConfigList = (LinkedHashMap<String, Object>) shardingRule.get("masterSlaveRules");
        if (ruleConfigList.isEmpty()) {
            new ConfigurationException("读写分离规则配置错误或未配置");
        }
        Set<String> instanceNames = ruleConfigList.keySet();
        if (instanceNames.isEmpty()) {
            new ConfigurationException("读写分离规则配置错误");
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
            MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(masterDataSourceName,
                    masterDataSourceName,
                    Arrays.asList(slaveDataSourceNames),
                    new LoadBalanceStrategyConfiguration(loadBalanceAlgorithm));

            masterSlaveRuleConfigurations.add(masterSlaveRuleConfig);
        }

        return masterSlaveRuleConfigurations;


    }


}

