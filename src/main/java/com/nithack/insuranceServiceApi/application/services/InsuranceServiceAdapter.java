package com.nithack.insuranceServiceApi.application.services;

import com.nithack.insuranceServiceApi.application.exception.InsuranceNotFoundException;
import com.nithack.insuranceServiceApi.application.port.InsuranceDataServicePort;
import com.nithack.insuranceServiceApi.application.port.InsuranceServicePort;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Adapter for insurance management operations,
 * implementing validation logic and database access.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceServiceAdapter implements InsuranceServicePort {

    private final InsuranceDataServicePort insuranceDataService;

    @Override
    public InsuranceEntity createInsurance(InsuranceEntity insuranceEntity) {
        log.info("[createInsurance] Starting insurance creation: {}", insuranceEntity.getName());
        try {
            final UUID insuranceId = UUID.randomUUID();
            insuranceEntity.setId(insuranceId);
            InsuranceEntity createdInsurance = insuranceDataService.save(insuranceEntity);
            log.info("[createInsurance] Insurance created successfully: {}", createdInsurance.getId());
            return createdInsurance;
        } catch (Exception e) {
            log.error("[createInsurance] Error creating insurance: {}", insuranceEntity.getName(), e);
            throw e;
        } finally {
            log.info("[createInsurance] Completed insurance creation: {}", insuranceEntity.getName());
        }
    }

    @Override
    public List<InsuranceEntity> getAllInsurances() {
        log.info("[getAllInsurances] Fetching all available insurances.");
        try {
            List<InsuranceEntity> insurances = insuranceDataService.findAll();
            if (insurances.isEmpty()) {
                log.warn("[getAllInsurances] No insurances found.");
                return List.of();
            }
            log.info("[getAllInsurances] Found {} insurances.", insurances.size());
            return insurances;
        } catch (Exception e) {
            log.error("[getAllInsurances] Error fetching all insurances.", e);
            throw e;
        } finally {
            log.info("[getAllInsurances] Completed fetching all insurances.");
        }
    }

    @Override
    public void deleteInsurance(UUID insuranceId) {
        log.info("[deleteInsurance] Starting deletion of insurance with ID: {}", insuranceId);
        try {
            if (!insuranceDataService.existsById(insuranceId)) {
                log.warn("[deleteInsurance] Insurance with ID {} not found.", insuranceId);
                throw new InsuranceNotFoundException(insuranceId);
            }
            insuranceDataService.deleteById(insuranceId);
            log.info("[deleteInsurance] Insurance with ID {} deleted successfully.", insuranceId);
        } catch (Exception e) {
            log.error("[deleteInsurance] Error deleting insurance with ID: {}", insuranceId, e);
            throw e;
        } finally {
            log.info("[deleteInsurance] Completed deletion of insurance with ID: {}", insuranceId);
        }
    }
}
