package com.nithack.insuranceServiceApi.infra.feign.client;

import com.nithack.insuranceServiceApi.application.dto.ClientDTO;
import com.nithack.insuranceServiceApi.infra.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "clientService",
        url = "${client.service.url:http://localhost:3030}",
        configuration = FeignClientConfig.class
)
public interface ClientServiceFeignClient {

    /**
     * Busca os dados do cliente pelo ID.
     *
     * @param clientId ID do cliente.
     * @return DTO com os dados do cliente.
     */
    @GetMapping("/clients/{clientId}")
    ClientDTO getClientById(@PathVariable("clientId") String clientId);
}