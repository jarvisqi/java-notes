package com.softmax.oauth2.security.config;

import com.zaxxer.hikari.HikariDataSource;
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
public class ShardingRuleConfig {

    private final Logger logger = LoggerFactory.getLogger(ShardingRuleConfig.class);

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
            new ConfigurationException("配置错误");
        }
        String names = datasource.get("names").toString();
        String[] datasourceNames = null;
        if (names.contains(",")) {
            datasourceNames = names.split(",");
        }
        if (datasourceNames == null || datasourceNames.length == 0) {
            new ConfigurationException("数据源配置错误");
        }

        for (String dsName : datasourceNames) {
            LinkedHashMap<String, String> dbConfigList = (LinkedHashMap<String, String>) datasource.get(dsName);
            String driverClassName = dbConfigList.get("driverClassName");
            //String type = dbConfigList.get("type");
            String jdbcUrl = dbConfigList.get("url");
            String dbUsername = dbConfigList.get("username");
            String dbPassword = dbConfigList.get("password");

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);

            dataSourceMap.put(dsName, dataSource);
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
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();

        LinkedHashMap<String, Object> ruleConfigList = (LinkedHashMap<String, Object>) shardingRule.get("tables");
        if (ruleConfigList.isEmpty()) {
            new ConfigurationException("规则配置错误");
        }
        Set<String> tbNames = ruleConfigList.keySet();
        for (String tbName : tbNames) {
            Object actualStrategyConfig = ruleConfigList.get(tbName);
            LinkedHashMap<String, Object> strategyConfigMap = (LinkedHashMap<String, Object>) actualStrategyConfig;
            //数据节点
            String actualDataNodes = strategyConfigMap.get(ACTUAL_DATANODES).toString();
            if (!StringUtils.isEmpty(actualDataNodes)) {
                new ConfigurationException(ACTUAL_DATANODES + "节点配置错误");
            }

            for (String configKey : strategyConfigMap.keySet()) {
                if (configKey.equals(ACTUAL_DATANODES)) {
                    continue;
                }
                // 配置表规则
                LinkedHashMap<String, Object> inlineConfigMap = (LinkedHashMap<String, Object>) strategyConfigMap.get(configKey);

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
                shardingRuleConfiguration.getTableRuleConfigs().add(ruleConfiguration);
            }
        }

        return shardingRuleConfiguration;

    }


}

