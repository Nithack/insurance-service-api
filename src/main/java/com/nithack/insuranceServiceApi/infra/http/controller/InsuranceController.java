package com.nithack.insuranceServiceApi.infra.http.controller;

import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.ClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.port.InsuranceServicePort;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceServicePort insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceDTO> createInsurance(
            @RequestBody
            @Valid InsuranceDTO insuranceDTO) {
        InsuranceEntity insurance = insuranceService.createInsurance(insuranceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(insurance);
    }

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        List<InsuranceDTO> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances);
    }

    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable UUID insuranceId) {
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/simulate")
    public ResponseEntity<ClientInsuranceDTO> simulateInsurance(
            @Valid @RequestBody InsuranceRequestDTO request
    ) {
        ClientInsuranceDTO simulation = insuranceService.simulateInsurance(request);
        return ResponseEntity.ok(simulation);
    }

    @PostMapping("/purchase")
    public ResponseEntity<ClientInsuranceDTO> purchaseInsurance(
                                                        @RequestBody
                                                        @Valid RequestClientInsuranceDTO request) {
        ClientInsuranceDTO contract = insuranceService.purchaseInsurance(request);
        return ResponseEntity.ok(contract);
    }
}