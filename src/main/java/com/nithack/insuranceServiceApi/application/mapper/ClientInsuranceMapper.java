package com.nithack.insuranceServiceApi.application.mapper;


import com.nithack.insuranceServiceApi.application.dto.ClientInsuranceDTO;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import com.nithack.insuranceServiceApi.infra.database.model.ClientInsuranceModel;

public class ClientInsuranceMapper {

    /**
     * Converte um objeto ClientInsurance para ClientInsuranceModel.
     *
     * @param clientInsurance Objeto da camada de domínio a ser convertido.
     * @return Objeto ClientInsuranceModel correspondente.
     */
    public static ClientInsuranceModel toModel(ClientInsuranceEntity clientInsurance) {
        return ClientInsuranceModel.builder()
                .id(clientInsurance.getId())
                .clientId(clientInsurance.getClientId())
                .insurance(InsuranceMapper.toModel(clientInsurance.getInsurance()))
                .startDate(clientInsurance.getStartDate())
                .endDate(clientInsurance.getEndDate())
                .status(clientInsurance.getStatus())
                .monthlyCost(clientInsurance.getMonthlyCost())
                .durationMonths(clientInsurance.getDurationMonths())
                .build();
    }

    /**
     * Converte um objeto ClientInsuranceModel para ClientInsurance.
     *
     * @param clientInsuranceModel Objeto do banco de dados a ser convertido.
     * @return Objeto ClientInsurance correspondente.
     */
    public static ClientInsuranceEntity toEntity(ClientInsuranceModel clientInsuranceModel) {
        return ClientInsuranceEntity.builder()
                .id(clientInsuranceModel.getId())
                .clientId(clientInsuranceModel.getClientId())
                .insurance(InsuranceMapper.toEntity(clientInsuranceModel.getInsurance()))
                .startDate(clientInsuranceModel.getStartDate())
                .endDate(clientInsuranceModel.getEndDate())
                .status(clientInsuranceModel.getStatus())
                .monthlyCost(clientInsuranceModel.getMonthlyCost())
                .durationMonths(clientInsuranceModel.getDurationMonths())
                .build();
    }

    /**
     * Converte um objeto ClientInsurance para ClientInsuranceDTO.
     *
     * @param clientInsurance A entidade ClientInsurance a ser convertida.
     * @return Um objeto ClientInsuranceDTO com os dados mapeados.
     */
    public static ClientInsuranceDTO toDTO(ClientInsuranceEntity clientInsurance) {
        final InsuranceEntity insurance = clientInsurance.getInsurance();
        return ClientInsuranceDTO.builder()
                .id(clientInsurance.getId())
                .clientId(clientInsurance.getClientId())
                .insuranceId(insurance.getId())
                .startDate(clientInsurance.getStartDate())
                .endDate(clientInsurance.getEndDate())
                .status(clientInsurance.getStatus())
                .monthlyCost(insurance.getMonthlyCost())
                .totalCost(clientInsurance.getTotalCost())
                .durationMonths(clientInsurance.getDurationMonths())
                .build();
    }
}