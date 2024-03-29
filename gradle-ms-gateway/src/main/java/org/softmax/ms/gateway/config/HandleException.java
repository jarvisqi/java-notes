package org.softmax.ms.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.softmax.ms.gateway.common.ApiConstant;
import org.softmax.ms.gateway.common.Result;
import org.softmax.ms.gateway.common.ResultUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 异常处理和rest请求配置
 *
 * @author Jarvis
 */
public class HandleException {

    @Resource
    private ObjectMapper objectMapper;

    public Mono<Void> writeError(ServerWebExchange exchange, String error) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Result result = ResultUtil.buildError(ApiConstant.NO_LOGIN_CODE, ApiConstant.NO_LOGIN_MESSAGE, request.getURI().getPath());
        String resultJson = null;
        DataBuffer buffer = null;
        try {
            objectMapper.writeValueAsString(result);
            buffer = response.bufferFactory().wrap(resultJson.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response.writeWith(Mono.just(buffer));
    }
}
