package com.softmax.oauth2.security.datasourconfig;

import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;

import java.util.ArrayList;
import java.util.List;


/**
 * Sharding 规则信息
 *
 * @author Jarvis
 * @date 2020/10/22
 */
public class ShardingStrategyInfo {

    /**
     * 绑定表规则列表名
     */
    private String bindingTableGroups;

    /**
     * 分片列名
     */
    private String databaseShardingColumn;

    /**
     * 分片算法
     */
    private String databaseAlgorithmExpression;

    /**
     * 分表列名
     */
    private String tableShardingColumn;

    /**
     * 分表算法
     */
    private String tableAlgorithmExpression;


    /**
     * 分表策略
     */
    private List<TableRuleConfiguration> tableRuleConfigurations = new ArrayList<>(4);

    public List<TableRuleConfiguration> getTableRuleConfigurations() {
        return tableRuleConfigurations;
    }

    public void setTableRuleConfigurations(List<TableRuleConfiguration> tableRuleConfigurations) {
        this.tableRuleConfigurations = tableRuleConfigurations;
    }


    public String getBindingTableGroups() {
        return bindingTableGroups;
    }

    public void setBindingTableGroups(String bindingTableGroups) {
        this.bindingTableGroups = bindingTableGroups;
    }

    public String getDatabaseShardingColumn() {
        return databaseShardingColumn;
    }

    public void setDatabaseShardingColumn(String databaseShardingColumn) {
        this.databaseShardingColumn = databaseShardingColumn;
    }

    public String getDatabaseAlgorithmExpression() {
        return databaseAlgorithmExpression;
    }

    public void setDatabaseAlgorithmExpression(String databaseAlgorithmExpression) {
        this.databaseAlgorithmExpression = databaseAlgorithmExpression;
    }

    public String getTableShardingColumn() {
        return tableShardingColumn;
    }

    public void setTableShardingColumn(String tableShardingColumn) {
        this.tableShardingColumn = tableShardingColumn;
    }

    public String getTableAlgorithmExpression() {
        return tableAlgorithmExpression;
    }

    public void setTableAlgorithmExpression(String tableAlgorithmExpression) {
        this.tableAlgorithmExpression = tableAlgorithmExpression;
    }
}
