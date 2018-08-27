package com.marvel.config;

import com.framework.common.BaseEnum;
import com.framework.handler.EnumValueTypeHandler;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Set;

/**
 * mybatis 数据源配置类
 *
 * @author : Jarvis
 * @date : 2018/5/30
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        ///sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        //加载全局mybatis的配置文件
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
                .getResource("classpath:mybatis/mybatis-config.xml"));
        // 扫描mapper配置文件
        Resource[] mapperResources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mybatis/mapper/*.xml");

        sqlSessionFactoryBean.setMapperLocations(mapperResources);
        sqlSessionFactoryBean.setTypeAliasesPackage("classpath*:/com/marvel/entity/*");

        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(BaseEnum.class), "com.marvel.entity");

        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
        for (Class<?> clazz : handlerSet) {
            if (BaseEnum.class.isAssignableFrom(clazz) && !BaseEnum.class.equals(clazz)) {
                sqlSessionFactoryBean.getObject()
                        .getConfiguration()
                        .getTypeHandlerRegistry()
                        .register(clazz, EnumValueTypeHandler.class);
            }
        }

        return sqlSessionFactoryBean.getObject();

    }

}
