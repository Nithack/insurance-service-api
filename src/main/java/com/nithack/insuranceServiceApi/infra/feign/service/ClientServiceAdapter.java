package com.nithack.insuranceServiceApi.infra.feign.service;

import com.nithack.insuranceServiceApi.application.dto.ClientDTO;
import com.nithack.insuranceServiceApi.application.port.ClientServicePort;
import com.nithack.insuranceServiceApi.infra.feign.client.ClientServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceAdapter implements ClientServicePort {

    private final ClientServiceFeignClient clientServiceFeignClient;

    public Optional<ClientDTO> getClientById(UUID clientId) {
        log.info("[getClientById] Fetching client data for ID: {}", clientId);
        try {
            ClientDTO clientDTO = clientServiceFeignClient.getClientById(clientId.toString());
            if (clientDTO == null) {
                log.warn("[getClientById] Client not found for ID: {}", clientId);
                return Optional.empty();
            }
            log.info("[getClientById] Successfully fetched client data for ID: {}", clientId);
            return Optional.of(clientDTO);
        } catch (Exception e) {
            log.error("[getClientById] Error fetching client data for ID: {}", clientId);
            throw e;
        } finally {
            log.info("[getClientById] Finished fetching client data for ID: {}", clientId);
        }
    }
}
