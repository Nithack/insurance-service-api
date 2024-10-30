package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface para o serviço de persistência de seguros, definindo operações para
 * criação, leitura, atualização e exclusão de dados de seguros.
 */
public interface InsuranceDataServicePort {

    /**
     * Exclui um seguro com base no ID fornecido.
     *
     * @param insuranceId UUID do seguro a ser excluído.
     */
    void deleteById(UUID insuranceId);

    /**
     * Retorna todos os seguros cadastrados no sistema.
     *
     * @return uma lista de objetos `Insurance`.
     */
    List<InsuranceEntity> findAll();

    /**
     * Busca um seguro pelo ID.
     *
     * @param insuranceId UUID do seguro a ser buscado.
     * @return um `Optional` contendo o seguro, caso exista; caso contrário, vazio.
     */
    Optional<InsuranceEntity> findById(UUID insuranceId);

    /**
     * Salva um novo seguro ou atualiza um seguro existente.
     *
     * @param insurance Objeto `Insurance` a ser salvo ou atualizado.
     * @return o objeto `Insurance` salvo ou atualizado.
     */
    InsuranceEntity save(InsuranceEntity insurance);

    /**
     * Verifica se um seguro existe com o ID fornecido.
     *
     * @param insuranceId UUID do seguro a ser verificado.
     * @return `true` se o seguro existir; `false` caso contrário.
     */
    boolean existsById(UUID insuranceId);
}
