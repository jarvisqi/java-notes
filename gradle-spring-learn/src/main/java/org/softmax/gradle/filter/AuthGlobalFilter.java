package org.softmax.gradle.filter;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.softmax.gradle.config.HandleException;
import org.softmax.gradle.config.IgnoreUrlsConfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局过滤器
 *
 * @author Jarvis
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private IgnoreUrlsConfig urlsConfig;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HandleException handleException;

    private String oauthTokenUrl = "http://ms-oauth2-server/oauth/check_token?token=";

    /**
     * 网关身份校验处理
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判断当前的请求是否在白名单中
        AntPathMatcher pathMatcher = new AntPathMatcher();
        boolean flag = false;
        String path = exchange.getRequest().getURI().getPath();
        for (String url : urlsConfig.getUrls()) {
            if (pathMatcher.match(url, path)) {
                flag = true;
                break;
            }
        }
        //白名单则不验证
        if (flag) {
            return chain.filter(exchange);
        }
        String accessToken = exchange.getRequest().getQueryParams().getFirst("access_token");
        //判断token是否为空
        if (StringUtils.isBlank(accessToken)) {
            return handleException.writeError(exchange, "用户未登录");
        }
        String checkTokenUrl = oauthTokenUrl.concat(accessToken);
        try {
            // 发送远程请求，验证 token
            ResponseEntity<String> entity = restTemplate.getForEntity(checkTokenUrl, String.class);
            //token 无效
            if (!entity.getStatusCode().equals(HttpStatus.OK)) {
                return handleException.writeError(exchange, "Token was not recognised, token: ".concat(accessToken));
            }
            if (StringUtils.isBlank(entity.getBody())) {
                return handleException.writeError(exchange,
                        "This token is invalid: ".concat(accessToken));
            }
        } catch (Exception e) {
            return handleException.writeError(exchange, "Token was not recognised, token: ".concat(accessToken));
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 网关过滤器的排序，数字越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
