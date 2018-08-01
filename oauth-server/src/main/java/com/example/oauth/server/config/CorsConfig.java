package com.example.oauth.server.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 跨域配置
 *
 * @author Jarvis
 * @date 2018/8/1
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //step 1
        corsConfiguration.addAllowedOrigin("*");
        //step 2
        corsConfiguration.addAllowedHeader("*");
        //step 3
        corsConfiguration.addAllowedMethod("*");

        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //step 4
        source.registerCorsConfiguration("/**", buildConfig());
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
