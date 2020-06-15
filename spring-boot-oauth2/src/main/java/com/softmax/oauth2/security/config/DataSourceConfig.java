package com.softmax.oauth2.security.config;

import com.softmax.oauth2.security.interceptors.CheckSQLInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author Jarvis
 */
@Configuration
@AutoConfigureAfter(DataSource.class)
public class DataSourceConfig {

    private final DataSource shardingDataSource;

    public DataSourceConfig(@Qualifier("shardingCreateDataSource") DataSource shardingDataSource) {
        this.shardingDataSource = shardingDataSource;
    }

    @Bean
    @Resource
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource);
        //此处需要扫描文件路径
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }


    /**
     * SQL 检查
     *
     * @return
     */
    @Bean
    public Interceptor getInterceptor() {
        return new CheckSQLInterceptor();
    }

}
