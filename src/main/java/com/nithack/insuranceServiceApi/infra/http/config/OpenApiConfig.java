package com.nithack.insuranceServiceApi.infra.http.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI insuranceServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Insurance Service API")
                        .description("API para gerenciamento de seguros, incluindo operações de criação, listagem, simulação e contratação.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Suporte Nithack")
                                .email("support@nithack.com")
                                .url("https://nithack.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente Local"),
                        new Server().url("https://api.nithack.com").description("Ambiente de Produção")
                ))
                .tags(List.of(
                        new Tag().name("Insurance Management").description("Operações para gerenciamento de seguros"),
                        new Tag().name("Simulation").description("Operações para simulação de planos de seguros"),
                        new Tag().name("Purchase").description("Operações de contratação de seguros")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Completa do Insurance Service")
                        .url("https://nithack.com/docs/insurance"));
    }
}