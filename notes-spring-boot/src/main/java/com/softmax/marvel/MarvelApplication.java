package com.softmax.marvel;

import com.framework.filters.ParamsFilter;
import com.softmax.marvel.handler.BeanTool;
import com.softmax.marvel.handler.order.HandlerProcessor;
import com.softmax.marvel.mapper.BaseMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
//MapperScan 导入  org.mybatis.springboot.annotation 会报错
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.DispatcherType;


/***
 *
 * @author : Jarvis
 * @date : 2018/02/01
 *
 */
@SpringBootApplication
@MapperScan(value = "com.softmax.marvel.mapper", markerInterface = BaseMapper.class)
public class MarvelApplication {

    public static void main(String[] args) {

//        SpringApplication application = new SpringApplication(MarvelApplication.class);
//        application.setBanner((environment, sourceClass, out) -> System.out.println("Marvel"));
//        application.setBannerMode(Banner.Mode.OFF);
//        application.run(args);

        SpringApplication.run(MarvelApplication.class, args);
    }


    @Bean
    public HandlerProcessor getHandlerProcessor() {
        return new HandlerProcessor();
    }

    @Bean
    public BeanTool getBeanTool() {
        return new BeanTool();
    }

    @Bean
    public FilterRegistrationBean parmsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ParamsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("paramsFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }
}

