package com.softmax.marvel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 配置重试
 */
@Configuration
@EnableRetry
public class RetryConfig {
}
