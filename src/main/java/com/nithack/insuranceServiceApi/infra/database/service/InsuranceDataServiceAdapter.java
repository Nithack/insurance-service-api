package com.nithack.insuranceServiceApi.application.services;

import com.nithack.insuranceServiceApi.application.mapper.InsuranceMapper;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import com.nithack.insuranceServiceApi.infra.database.model.InsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.repository.InsuranceRepository;
import com.nithack.insuranceServiceApi.application.port.InsuranceDataServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter para operações de persistência de dados de seguros,
 * implementando lógica de mapeamento e acesso ao banco de dados.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceDataServiceAdapter implements InsuranceDataServicePort {

    private final InsuranceRepository insuranceRepository;

    @Override
    public void deleteById(UUID insuranceId) {
        log.info("[deleteById] Deleting insurance with id: {}", insuranceId);
        try {
            insuranceRepository.deleteById(insuranceId);
            log.info("[deleteById] Successfully deleted insurance with id: {}", insuranceId);
        } catch (Exception e) {
            log.error("[deleteById] Error deleting insurance with id: {}", insuranceId, e);
            throw e;
        } finally {
            log.info("[deleteById] Finished deleting insurance with id: {}", insuranceId);
        }
    }

    /**
     * Retorna todos os seguros cadastrados, convertendo-os para Insurance.
     *
     * @return lista de Insurance.
     */
    public List<InsuranceEntity> findAll() {
        log.info("[findAll] Retrieving all insurances from database");
        try {
            List<InsuranceModel> insuranceModels = insuranceRepository.findAll();
            if (insuranceModels.isEmpty()) {
                log.warn("[findAll] No insurances found");
                return List.of();
            }
            log.info("[findAll] Successfully found {} insurances, start mapping", insuranceModels.size());
            List<InsuranceEntity> insurances = insuranceModels.stream()
                    .map(InsuranceMapper::toEntity)
                    .collect(Collectors.toList());
            log.info("[findAll] Successfully mapped {} insurances", insurances.size());
            return insurances;
        } catch (Exception e) {
            log.error("[findAll] Error retrieving all insurances from database", e);
            throw e;
        } finally {
            log.info("[findAll] Finished retrieving all insurances from database");
        }
    }

    /**
     * Busca um seguro pelo ID, retornando-o como Insurance.
     *
     * @param insuranceId UUID do seguro.
     * @return Optional com Insurance, ou vazio se não encontrado.
     */
    public Optional<InsuranceEntity> findById(UUID insuranceId) {
        log.info("[findById] Retrieving insurance with id: {}", insuranceId);
        try {
            Optional<InsuranceModel> insuranceModel = insuranceRepository.findById(insuranceId);
            log.info("[findById] Start mapping insurance with id: {}", insuranceId);
            Optional<InsuranceEntity> insurance = insuranceModel.map(InsuranceMapper::toEntity);
            log.info("[findById] Successfully mapped insurance with id: {}", insuranceId);
            return insurance;
        } catch (Exception e) {
            log.error("[findById] Error retrieving insurance with id: {}", insuranceId, e);
            throw e;
        } finally {
            log.info("[findById] Finished retrieving insurance with id: {}", insuranceId);
        }
    }

    /**
     * Salva ou atualiza um seguro, mapeando de Insurance para InsuranceModel.
     *
     * @param insurance o seguro a ser salvo.
     * @return Insurance salvo.
     */
    public InsuranceEntity save(InsuranceEntity insurance) {
        log.info("[save] Saving insurance with id: {}", insurance.getId());
        try {
            InsuranceModel insuranceModel = InsuranceMapper.toModel(insurance);
            InsuranceModel savedInsurance = insuranceRepository.save(insuranceModel);
            log.info("[save] Successfully saved insurance with id: {}", savedInsurance.getId());
            log.info("[save] Start mapping saved insurance with id: {}", savedInsurance.getId());
            InsuranceEntity savedInsuranceEntity = InsuranceMapper.toEntity(savedInsurance);
            log.info("[save] Successfully mapped saved insurance with id: {}", savedInsurance.getId());
            return savedInsuranceEntity;
        } catch (Exception e) {
            log.error("[save] Error saving insurance with id: {}", insurance.getId());
            throw e;
        } finally {
            log.info("[save] Finished saving insurance with id: {}", insurance.getId());
        }
    }

    /**
     * Verifica se um seguro existe pelo ID.
     *
     * @param insuranceId UUID do seguro.
     * @return true se o seguro existir, false caso contrário.
     */
    public boolean existsById(UUID insuranceId) {
        log.info("[existsById] Checking if insurance exists with id: {}", insuranceId);
        try {
            boolean exists = insuranceRepository.existsById(insuranceId);
            log.info("[existsById] Insurance with id: {} exists: {}", insuranceId, exists);
            return exists;
        } catch (Exception e) {
            log.error("[existsById] Error checking existence of insurance with id: {}", insuranceId);
            throw e;
        }
    }
}
