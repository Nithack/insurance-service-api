package com.nithack.insuranceServiceApi.application.services;

import com.nithack.insuranceServiceApi.infra.database.service.InsuranceDataServicePort;
import com.nithack.insuranceServiceApi.application.port.InsuranceServicePort;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Adapter para operações de gerenciamento de seguros,
 * implementando lógica de validação e acesso ao banco de dados.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceServiceAdapter implements InsuranceServicePort {

    private final InsuranceDataServicePort insuranceDataService;

    @Override
    public InsuranceEntity createInsurance(InsuranceEntity insuranceEntity) {
        log.info("[createInsurance] Iniciando criação de seguro: {}", insuranceEntity.getName());
        try {
            InsuranceEntity createdInsurance = insuranceDataService.save(insuranceEntity);
            log.info("[createInsurance] Seguro criado com sucesso: {}", createdInsurance.getId());
            return createdInsurance;
        } catch (Exception e) {
            log.error("[createInsurance] Erro ao criar seguro: {}", insuranceEntity.getName(), e);
            throw e;
        } finally {
            log.info("[createInsurance] Concluída criação de seguro: {}", insuranceEntity.getName());
        }
    }

    @Override
    public List<InsuranceEntity> getAllInsurances() {
        log.info("[getAllInsurances] Buscando todos os seguros disponíveis.");
        try {
            List<InsuranceEntity> insurances = insuranceDataService.findAll();
            if (insurances.isEmpty()) {
                log.warn("[getAllInsurances] Nenhum seguro encontrado.");
                return List.of();
            }
            log.info("[getAllInsurances] {} seguros encontrados.", insurances.size());
            return insurances;
        } catch (Exception e) {
            log.error("[getAllInsurances] Erro ao buscar todos os seguros.", e);
            throw e;
        } finally {
            log.info("[getAllInsurances] Concluída busca de todos os seguros.");
        }
    }

    @Override
    public void deleteInsurance(UUID insuranceId) {
        log.info("[deleteInsurance] Iniciando exclusão de seguro com ID: {}", insuranceId);
        try {
            if (!insuranceDataService.existsById(insuranceId)) {
                log.warn("[deleteInsurance] Seguro com ID {} não encontrado.", insuranceId);
                throw new IllegalArgumentException("Seguro não encontrado para o ID: " + insuranceId);
            }
            insuranceDataService.deleteById(insuranceId);
            log.info("[deleteInsurance] Seguro com ID {} excluído com sucesso.", insuranceId);
        } catch (Exception e) {
            log.error("[deleteInsurance] Erro ao excluir seguro com ID: {}", insuranceId, e);
            throw e;
        } finally {
            log.info("[deleteInsurance] Concluída exclusão de seguro com ID: {}", insuranceId);
        }
    }
}
