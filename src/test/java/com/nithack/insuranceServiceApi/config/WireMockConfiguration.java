package com.nithack.insuranceServiceApi.config;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfiguration {

    @RegisterExtension
    public static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(3333))
            .build();

    @Bean
    public WireMockExtension wireMockExtension() {
        return wireMock;
    }
}