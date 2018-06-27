package com.marvel.config;

import com.framework.common.BaseEnum;
import com.framework.handler.EnumValueTypeHandler;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Set;

/**
 * @author : Jarvis
 * @date : 2018/5/30
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisConfig {
    private String configLocation;
    private String mapperLocations;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        ///sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        //加载全局mybatis的配置文件
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
                .getResource("classpath:mybatis/mybatis-config.xml"));
        // 扫描mapper配置文件
        org.springframework.core.io.Resource[] mapperResources = new PathMatchingResourcePatternResolver()
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
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        // 取得类型转换注册器
        ///TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        // 注册默认枚举转换器
        ///typeHandlerRegistry.setDefaultEnumTypeHandler(AutoEnumTypeHanlder.class);

        return sqlSessionFactory;

    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
