package com.softmax.oauth2server.security;

import com.softmax.oauth2server.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Oauth2 配置
 *
 * @author Jarvis
 * @date 2018/9/4
 */
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter implements ApplicationRunner {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthUserService userService;

    @Autowired
    private ClientDetailsService clientDetails;

    @Qualifier("jdbcTokenStore")
    private TokenStore tokenStore;

    /**
     * 配置token获取合验证时的策略
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置oauth2的 client信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // authorizedGrantTypes 有4种，这里只开启2种
        /*
            authorization_code：授权码类型。
            implicit：隐式授权类型。
            password：资源所有者（即用户）密码类型。
            client_credentials：客户端凭据（客户端ID以及Key）类型。
            refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。

         */
        // secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写
//        clients.inMemory()
//                //clientId：（必须的）用来标识客户的Id。
//                .withClient("testclient")
//                //secret：（需要值得信任的客户端）客户端安全码，如果有的话。
//                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("testclient"))
//                //scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
//                .scopes("test")
//                //authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
//                .authorizedGrantTypes("authorization_code", "refresh_token").authorities();
        //设置OAuth2的client信息也使用数据库存储和读取
        clients.jdbc(dataSource);

    }

    /**
     * 配置tokenStore
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /// endpoints.authenticationManager(authenticationManager).tokenStore(memoryTokenStore());
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore);
        endpoints.userDetailsService(userService);
        endpoints.setClientDetailsService(clientDetails);

        //配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 1天
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        endpoints.tokenServices(tokenServices);
    }

    /**
     * 使用最基本的InMemoryTokenStore生成token
     *
     * @return
     */
    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 使用JdbcTokenStore把token存储到数据库中，RedisTokenStore的使用方法也类似
     *
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 声明 ClientDetails实现
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }


    /**
     * 添加预置的client
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        try {
            clientDetailsService.loadClientByClientId("softmax");
        } catch (ClientRegistrationException e) {
            BaseClientDetails details = new BaseClientDetails();
            details.setClientId("softmax");
            details.setClientSecret("123456");
            details.setScope(Arrays.asList("softmax1", "softmax2"));
            details.setAutoApproveScopes(Arrays.asList("softmax"));
            details.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "refresh_token"));
            clientDetailsService.addClientDetails(details);
        }
    }
}