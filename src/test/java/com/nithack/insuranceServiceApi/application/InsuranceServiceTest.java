package com.nithack.insuranceServiceApi.application;


import com.nithack.insuranceServiceApi.application.exception.InsuranceNotFoundException;
import com.nithack.insuranceServiceApi.application.services.InsuranceDataServiceAdapter;
import com.nithack.insuranceServiceApi.application.services.InsuranceServiceAdapter;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import com.nithack.insuranceServiceApi.infra.database.model.InsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.repository.InsuranceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    private InsuranceServiceAdapter insuranceService;

    @BeforeEach
    void setUp() {
        InsuranceDataServiceAdapter insuranceDataService = new InsuranceDataServiceAdapter(insuranceRepository);
        insuranceService = new InsuranceServiceAdapter(insuranceDataService);
    }

    @Test
    @DisplayName("Should create insurance successfully with valid data")
    void createInsurance_ShouldReturnInsurance_WhenDataIsValid() {
        UUID insuranceId = UUID.randomUUID();
        InsuranceEntity insuranceEntity = getInsuranceEntity(insuranceId);
        InsuranceModel insuranceModel = getInsuranceModel(insuranceId);

        when(insuranceRepository.save(any(InsuranceModel.class))).thenReturn(insuranceModel);

        InsuranceEntity createdInsurance = insuranceService.createInsurance(insuranceEntity);

        assertThat(createdInsurance).isNotNull();
        assertThat(createdInsurance.getId()).isEqualTo(insuranceId);
        verify(insuranceRepository, times(1)).save(any(InsuranceModel.class));
    }

    @Test
    @DisplayName("Should retrieve all insurances successfully")
    void getAllInsurances_ShouldReturnListOfInsurances_WhenInsurancesExist() {
        InsuranceModel insuranceModel1 = getInsuranceModel(UUID.randomUUID());
        InsuranceModel insuranceModel2 = getInsuranceModel(UUID.randomUUID());
        when(insuranceRepository.findAll()).thenReturn(List.of(insuranceModel1, insuranceModel2));

        List<InsuranceEntity> insurances = insuranceService.getAllInsurances();

        assertThat(insurances).hasSize(2);
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no insurances exist")
    void getAllInsurances_ShouldReturnEmptyList_WhenNoInsurancesExist() {
        when(insuranceRepository.findAll()).thenReturn(List.of());

        List<InsuranceEntity> insurances = insuranceService.getAllInsurances();

        assertThat(insurances).isEmpty();
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete insurance successfully when it exists")
    void deleteInsurance_ShouldDeleteInsurance_WhenInsuranceExists() {
        UUID insuranceId = UUID.randomUUID();
        when(insuranceRepository.existsById(insuranceId)).thenReturn(true);

        insuranceService.deleteInsurance(insuranceId);

        verify(insuranceRepository, times(1)).deleteById(insuranceId);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing insurance")
    void deleteInsurance_ShouldThrowException_WhenInsuranceDoesNotExist() {
        UUID insuranceId = UUID.randomUUID();
        when(insuranceRepository.existsById(insuranceId)).thenReturn(false);

        assertThrows(InsuranceNotFoundException.class, () -> insuranceService.deleteInsurance(insuranceId));
        verify(insuranceRepository, never()).deleteById(insuranceId);
    }

    private InsuranceEntity getInsuranceEntity(UUID id) {
        return InsuranceEntity.builder()
                .id(id)
                .name("Sample Insurance")
                .coverageAmount(100000)
                .monthlyCost(200)
                .benefits(List.of("Benefit A", "Benefit B"))
                .build();
    }

    private InsuranceModel getInsuranceModel(UUID id) {
        return InsuranceModel.builder()
                .id(id)
                .name("Sample Insurance")
                .coverageAmount(100000)
                .monthlyCost(200)
                .benefits(List.of("Benefit A", "Benefit B"))
                .build();
    }
}