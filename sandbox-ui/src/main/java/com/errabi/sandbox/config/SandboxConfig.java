package com.errabi.sandbox.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SandboxConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("sandbox");
    }
}
