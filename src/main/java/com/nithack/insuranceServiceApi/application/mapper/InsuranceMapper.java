package com.nithack.insuranceServiceApi.application.mapper;

import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import com.nithack.insuranceServiceApi.infra.database.model.InsuranceModel;

public class InsuranceMapper {

    /**
     * Converte um objeto Insurance para InsuranceModel.
     *
     * @param insurance Objeto da camada de domínio a ser convertido.
     * @return Objeto InsuranceModel correspondente.
     */
    public static InsuranceModel toModel(InsuranceEntity insurance) {
        return InsuranceModel.builder()
                .id(insurance.getId())
                .name(insurance.getName())
                .coverageAmount(insurance.getCoverageAmount())
                .monthlyCost(insurance.getMonthlyCost())
                .benefits(insurance.getBenefits())
                .build();
    }

    /**
     * Converte um objeto InsuranceModel para Insurance.
     *
     * @param insuranceModel Objeto do banco de dados a ser convertido.
     * @return Objeto Insurance correspondente.
     */
    public static InsuranceEntity toEntity(InsuranceModel insuranceModel) {
        return InsuranceEntity.builder()
                .id(insuranceModel.getId())
                .name(insuranceModel.getName())
                .coverageAmount(insuranceModel.getCoverageAmount())
                .monthlyCost(insuranceModel.getMonthlyCost())
                .benefits(insuranceModel.getBenefits())
                .build();
    }

    /**
     * Converte um objeto Insurance para InsuranceDTO.
     *
     * @param insurance A entidade Insurance a ser convertida.
     * @return Um objeto InsuranceDTO com os dados mapeados.
     */
    public static InsuranceDTO toDTO(InsuranceEntity insurance) {
        return InsuranceDTO.builder()
                .id(insurance.getId())
                .name(insurance.getName())
                .coverageAmount(insurance.getCoverageAmount())
                .monthlyCost(insurance.getMonthlyCost())
                .benefits(insurance.getBenefits())
                .build();
    }

    /**
     * Converte um objeto InsuranceDTO para Insurance.
     *
     * @param insuranceDTO O DTO InsuranceDTO a ser convertido.
     * @return Uma entidade Insurance com os dados mapeados.
     */
    public static InsuranceEntity toEntity(InsuranceDTO insuranceDTO) {
        return InsuranceEntity.builder()
                .id(insuranceDTO.getId())
                .name(insuranceDTO.getName())
                .coverageAmount(insuranceDTO.getCoverageAmount())
                .monthlyCost(insuranceDTO.getMonthlyCost())
                .benefits(insuranceDTO.getBenefits())
                .build();
    }
}