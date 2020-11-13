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
import org.springframework.core.io.DefaultResourceLoader;
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
        //加载全局mybatis的配置文件
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
                .getResource("classpath:mybatis/mybatis-config.xml"));
        // 扫描mapper配置文件
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mybatis/mapper/*.xml"));

        sqlSessionFactoryBean.setDataSource(shardingDataSource);
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
