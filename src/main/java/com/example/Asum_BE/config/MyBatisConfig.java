package com.example.Asum_BE.config;

import com.example.Asum_BE.common.interceptor.SlowQueryInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public Interceptor slowQueryInterceptor() {
        return new SlowQueryInterceptor();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(Interceptor slowQueryInterceptor) {
        return configuration -> configuration.addInterceptor(slowQueryInterceptor);
    }
}
