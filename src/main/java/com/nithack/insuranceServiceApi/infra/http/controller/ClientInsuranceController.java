package com.nithack.insuranceServiceApi.infra.http.controller;

import com.nithack.insuranceServiceApi.application.dto.ClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.mapper.ClientInsuranceMapper;
import com.nithack.insuranceServiceApi.application.port.ClientInsuranceServicePort;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import com.nithack.insuranceServiceApi.infra.http.doc.ClientInsuranceControllerAPIDoc;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/clients/insurances")
@RequiredArgsConstructor
public class ClientInsuranceController implements ClientInsuranceControllerAPIDoc {

    private final ClientInsuranceServicePort clientInsuranceService;

    @PostMapping("/simulate")
    public ResponseEntity<ClientInsuranceDTO> simulateInsurance(
            @Valid @RequestBody RequestClientInsuranceDTO request) {
        log.info("[simulateInsurance] Simulating insurance for client with ID: {}", request.getClientId());
        ClientInsuranceEntity simulation = clientInsuranceService.simulateInsurance(request);
        return ResponseEntity.ok(ClientInsuranceMapper.toDTO(simulation));
    }

    @PostMapping("/purchase")
    public ResponseEntity<ClientInsuranceDTO> purchaseInsurance(
            @Valid @RequestBody RequestClientInsuranceDTO request) {
        log.info("[purchaseInsurance] Purchasing insurance for client with ID: {}", request.getClientId());
        ClientInsuranceEntity contract = clientInsuranceService.purchaseInsurance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientInsuranceMapper.toDTO(contract));
    }

    @DeleteMapping("/{clientId}/{clientInsuranceId}")
    public ResponseEntity<Void> deleteClientInsurance(
            @PathVariable UUID clientId,
            @PathVariable UUID clientInsuranceId) {
        log.info("[deleteClientInsurance] Deleting insurance with ID: {} for client with ID: {}", clientInsuranceId, clientId);
        clientInsuranceService.deleteClientInsurance(clientId, clientInsuranceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientInsuranceDTO> getClientInsurance(@PathVariable UUID clientId) {
        log.info("[getClientInsurance] Retrieving insurance for client with ID: {}", clientId);
        Optional<ClientInsuranceEntity> clientInsurance = clientInsuranceService.getByClientId(clientId);
        return clientInsurance.map( clientInsuranceEntity -> ResponseEntity.ok(ClientInsuranceMapper.toDTO(clientInsuranceEntity)))
                .orElse(ResponseEntity.noContent().build());
    }
}
