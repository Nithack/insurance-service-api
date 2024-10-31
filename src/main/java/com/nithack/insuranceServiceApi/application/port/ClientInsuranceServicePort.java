package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço para operações de gerenciamento de seguros de cliente.
 * Define métodos para simulação, contratação, exclusão e consulta de seguros vinculados a um cliente.
 */
public interface ClientInsuranceServicePort {

    /**
     * Realiza uma simulação de seguro para um cliente, com base nas informações de entrada.
     *
     * @param request Dados de entrada contendo IDs do cliente e do seguro, e a duração em meses.
     * @return A entidade de seguro de cliente simulada, contendo o custo total e detalhes do plano.
     */
    ClientInsuranceEntity simulateInsurance(RequestClientInsuranceDTO request);

    /**
     * Contrata um seguro para um cliente específico.
     * Realiza as devidas verificações e armazena a contratação do seguro.
     *
     * @param request Dados de entrada contendo IDs do cliente e do seguro, e a duração em meses.
     * @return A entidade de seguro de cliente contratada, com informações detalhadas.
     */
    ClientInsuranceEntity purchaseInsurance(RequestClientInsuranceDTO request);

    /**
     * Exclui o seguro de um cliente específico, identificado pelo ID do cliente e do seguro.
     *
     * @param clientId           ID do cliente.
     * @param clientInsuranceId  ID do seguro de cliente.
     */
    void deleteClientInsurance(UUID clientId, UUID clientInsuranceId);

    /**
     * Recupera o seguro de um cliente com base no ID do cliente.
     *
     * @param clientId ID do cliente.
     * @return Um Optional contendo a entidade de seguro do cliente, se existir.
     */
    Optional<ClientInsuranceEntity> getByClientId(UUID clientId);
}