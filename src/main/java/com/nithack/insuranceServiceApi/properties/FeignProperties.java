package com.nithack.insuranceServiceApi.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "feign")
public class FeignProperties {
    private Integer period;
    private Long maxPeriodBetweenAttempts;
    private Integer maxAttempts;
}
