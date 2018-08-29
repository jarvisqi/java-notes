package com.softmax.tutorial.config;

import com.softmax.framework.common.BaseEnum;
import com.softmax.framework.handler.EnumValueTypeHandler;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Set;

/**
 * master 据库
 *
 * @author Jarvis
 * @date 2018/8/24
 */
@Configuration
@MapperScan(basePackages = "com.softmax.tutorial.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MyBatisMasterConfig {

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master", ignoreInvalidFields = true)
    public DataSource masterDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        try {
            //加载全局mybatis的配置文件
            factoryBean.setConfigLocation(new DefaultResourceLoader()
                    .getResource("classpath:mybatis/mybatis-config.xml"));
            // 扫描mapper配置文件
            Resource[] mapperResources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:/mybatis/mapper/master/*.xml");
            factoryBean.setMapperLocations(mapperResources);
            factoryBean.setTypeAliasesPackage("classpath*:/com/softmax/tutorial/entity/*");

            ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
            resolverUtil.find(new ResolverUtil.IsA(BaseEnum.class), "com.softmax.tutorial.entity");
            Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
            for (Class<?> clazz : handlerSet) {
                if (BaseEnum.class.isAssignableFrom(clazz) && !BaseEnum.class.equals(clazz)) {
                    factoryBean.getObject()
                            .getConfiguration()
                            .getTypeHandlerRegistry()
                            .register(clazz, EnumValueTypeHandler.class);
                }
            }
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Primary
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager writeTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }
}
