package com.softmax.marvel.config;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * apollo配置
 *
 * @author Jarvis
 * @date 2019/04/25
 */
@Configuration
@EnableApolloConfig
public class ApolloConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApolloConfig.class);

    @Autowired
    private RefreshScope refreshScope;

    @ApolloConfigChangeListener(ConfigConsts.NAMESPACE_APPLICATION)
    public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("before refresh");
        for (String changedKey : changeEvent.changedKeys()) {
            logger.info("===============================================================");
            logger.info("changedKey:{} value:{}", changedKey, changeEvent.getChange(changedKey));
            ConfigChange configChange = changeEvent.getChange(changedKey);
            configChange.getOldValue();
        }
        refreshScope.refreshAll();
        logger.info("after refresh");
    }
}
