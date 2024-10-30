package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta para o serviço de gerenciamento de seguros de clientes.
 * Define operações básicas para criação, atualização, exclusão e consulta de seguro de clientes.
 */
public interface ClientInsuranceDataServicePort {

    /**
     * Cria um novo seguro para um cliente.
     *
     * @param clientInsurance a entidade ClientInsuranceEntity contendo os dados do seguro do cliente a ser criado.
     * @return a entidade ClientInsuranceEntity criada.
     */
    ClientInsuranceEntity create(ClientInsuranceEntity clientInsurance);

    /**
     * Atualiza as informações de um seguro existente para um cliente.
     *
     * @param clientInsurance a entidade ClientInsuranceEntity contendo os dados atualizados do seguro do cliente.
     * @return a entidade ClientInsuranceEntity atualizada.
     */
    ClientInsuranceEntity update(ClientInsuranceEntity clientInsurance);

    /**
     * Exclui um seguro de cliente com base no identificador único (ID).
     *
     * @param insuranceId o UUID do seguro do cliente a ser excluído.
     */
    void deleteById(UUID insuranceId);

    /**
     * Busca o seguro de um cliente com base no identificador único do cliente (clientId).
     *
     * @param clientId o UUID do cliente.
     * @return um Optional contendo a entidade ClientInsuranceEntity, caso encontrada, ou vazio se não encontrada.
     */
    Optional<ClientInsuranceEntity> findByClientId(UUID clientId);

    /**
     * Deleta o seguro de um cliente com base no ID do cliente e do ID do seguro.
     * @param clientInsuranceId ID do seguro
     * @param clientId ID do cliente
     */
    void deleteByIdAndClientId(UUID clientInsuranceId, UUID clientId);

    /**
     * Verifica se o cliente ja possui um seguro.
     *
     * @param clientId o UUID do cliente.
     * @return true se o seguro de cliente existir, false caso contrário.
     */
    boolean existsByClientId(UUID clientId);
}