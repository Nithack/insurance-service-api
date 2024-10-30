package com.nithack.insuranceServiceApi.infra.feign.config;

import com.nithack.insuranceServiceApi.properties.FeignProperties;
import feign.Retryer;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor

public class FeignClientConfig {

    private final FeignProperties feignProperties;

    @Bean
    public OkHttpClient client()  {
        return new OkHttpClient();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                feignProperties.getPeriod(),
                TimeUnit.SECONDS.toMillis(feignProperties.getMaxPeriodBetweenAttempts()),
                feignProperties.getMaxAttempts()
        );
    }
}
