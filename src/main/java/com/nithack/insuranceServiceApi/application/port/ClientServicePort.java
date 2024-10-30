package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.application.dto.ClientDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de serviço para interagir com o serviço de clientes.
 */
public interface ClientServicePort {

    /**
     * Busca os dados do cliente pelo ID.
     *
     * @param clientId ID do cliente.
     * @return DTO contendo os dados do cliente.
     */
    Optional<ClientDTO> getClientById(UUID clientId);
}