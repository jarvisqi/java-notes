package com.softmax.oauth2.security.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author Jarvis
 */
@Configuration
@AutoConfigureAfter(DataSource.class)
public class ShardingDataSourceConfig {

    private final DataSource shardingDataSource;

    public ShardingDataSourceConfig(@Qualifier("shardingCreateDataSource") DataSource shardingDataSource) {
        this.shardingDataSource = shardingDataSource;
    }

    /**
     * 设置数据源为sharding 数据源
     *
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource);

        return sqlSessionFactoryBean.getObject();
    }

}
