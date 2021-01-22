package com.softmax.oauth2.security.datasourconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Range;
import lombok.SneakyThrows;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 精确分表算法
 * 根据 order id 来指定分表规则
 *
 * @author Jarvis
 * @date 2020/10/22
 */
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    private final Logger logger = LoggerFactory.getLogger(PreciseModuloShardingTableAlgorithm.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String each : collection) {
            if (each.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        logger.info(String.valueOf(preciseShardingValue.getValue()));

        throw new UnsupportedOperationException();
    }

    /**
     * 实现between and查询
     */
    @SneakyThrows
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        logger.info("collection:{}", mapper.writeValueAsString(collection));
        logger.info("rangeShardingValue:{}", mapper.writeValueAsString(rangeShardingValue));

        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith(i % collection.size() + "")) {
                    collect.add(each);
                }
            }
        }

        return collect;
    }
}
