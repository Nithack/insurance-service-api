package com.nithack.insuranceServiceApi.infra.http.controller;

import com.nithack.insuranceServiceApi.application.dto.ClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.mapper.ClientInsuranceMapper;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import com.nithack.insuranceServiceApi.infra.http.doc.ClientInsuranceControllerAPIDoc;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients/insurances")
public class ClientInsuranceController implements ClientInsuranceControllerAPIDoc {

    private final ClientInsuranceServicePort clientInsuranceService;

    @PostMapping("/simulate")
    public ResponseEntity<ClientInsuranceDTO> simulateInsurance(
            @Valid @RequestBody RequestClientInsuranceDTO request
    ) {

        ClientInsuranceEntity simulation = clientInsuranceService.simulateInsurance(request);
        return ResponseEntity.ok(ClientInsuranceMapper.toDTO(simulation));
    }

    @PostMapping("/purchase")
    public ResponseEntity<ClientInsuranceDTO> purchaseInsurance(
            @RequestBody
            @Valid RequestClientInsuranceDTO request) {
        ClientInsuranceEntity contract = clientInsuranceService.purchaseInsurance(request);
        return ResponseEntity.ok(ClientInsuranceMapper.toDTO(contract));
    }
}
