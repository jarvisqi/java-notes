package com.softmax.oauth2.security.datasourconfig;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精确分片算法
 *
 * @author Jarvis
 * @date 2020/10/22
 */
public class PreciseModuloShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> dataSourceNames, PreciseShardingValue<Long> preciseShardingValue) {
        for (String dataSourceName : dataSourceNames) {
            if (dataSourceName.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                return dataSourceName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
