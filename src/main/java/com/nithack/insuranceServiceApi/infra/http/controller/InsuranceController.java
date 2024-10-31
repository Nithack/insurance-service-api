package com.nithack.insuranceServiceApi.infra.http.controller;

import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import com.nithack.insuranceServiceApi.application.mapper.InsuranceMapper;
import com.nithack.insuranceServiceApi.infra.http.doc.InsuranceControllerAPIDoc;
import com.nithack.insuranceServiceApi.application.port.InsuranceServicePort;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController implements InsuranceControllerAPIDoc {

    private final InsuranceServicePort insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceDTO> createInsurance(
            @RequestBody
            @Valid InsuranceDTO insuranceDTO) {
        log.info("[createInsurance] Creating insurance: {}", insuranceDTO);
        InsuranceEntity insurance = insuranceService.createInsurance(InsuranceMapper.toEntity(insuranceDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(InsuranceMapper.toDTO(insurance));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        log.info("[getAllInsurances] Retrieving all insurances");
        List<InsuranceEntity> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances.stream().map(InsuranceMapper::toDTO).toList());
    }

    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable UUID insuranceId) {
        log.info("[deleteInsurance] Deleting insurance with ID: {}", insuranceId);
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.noContent().build();
    }

}