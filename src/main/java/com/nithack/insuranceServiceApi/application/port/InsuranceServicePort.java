package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define o contrato para o serviço de gerenciamento de seguros.
 * Fornece operações para criar, listar, deletar, seguros
 */
public interface InsuranceServicePort {

    /**
     * Cria um novo seguro com base nas informações fornecidas no DTO.
     *
     * @param insuranceDTO Dados do seguro a ser criado.
     * @return A entidade de seguro criada.
     */
    InsuranceEntity createInsurance(InsuranceEntity insuranceDTO);

    /**
     * Lista todos os seguros disponíveis no sistema.
     *
     * @return Uma lista de objetos InsuranceDTO representando todos os seguros disponíveis.
     */
    List<InsuranceEntity> getAllInsurances();

    /**
     * Deleta um seguro específico identificado pelo seu ID.
     *
     * @param insuranceId ID do seguro a ser excluído.
     */
    void deleteInsurance(UUID insuranceId);

}
