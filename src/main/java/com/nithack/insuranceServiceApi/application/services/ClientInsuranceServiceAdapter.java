package com.nithack.insuranceServiceApi.application.services;

import com.nithack.insuranceServiceApi.application.dto.ClientDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.exception.ClientInsuranceAlreadyExistsException;
import com.nithack.insuranceServiceApi.application.exception.ClientNotFoundException;
import com.nithack.insuranceServiceApi.application.exception.InsuranceNotFoundException;
import com.nithack.insuranceServiceApi.application.port.ClientInsuranceDataServicePort;
import com.nithack.insuranceServiceApi.application.port.ClientInsuranceServicePort;
import com.nithack.insuranceServiceApi.application.port.ClientServicePort;
import com.nithack.insuranceServiceApi.application.port.InsuranceDataServicePort;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import com.nithack.insuranceServiceApi.domain.entity.InsuranceEntity;
import com.nithack.insuranceServiceApi.domain.enums.InsuranceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientInsuranceServiceAdapter implements ClientInsuranceServicePort {

    private final ClientInsuranceDataServicePort clientInsuranceDataService;
    private final InsuranceDataServicePort insuranceDataService;
    private final ClientServicePort clientService;

    @Override
    public ClientInsuranceEntity simulateInsurance(RequestClientInsuranceDTO request) {
        log.info("[simulateInsurance] Initiating insurance simulation for clientId: {}, insuranceId: {}",
                request.getClientId(), request.getInsuranceId());
        try {
            final boolean hasClientInsurance = clientInsuranceDataService.existsByClientId(request.getClientId());
            if (hasClientInsurance) {
                throw new ClientInsuranceAlreadyExistsException(request.getClientId());
            }
            ClientInsuranceEntity simulation = getClientInsurance(request, InsuranceStatus.SIMULATED);

            log.info("[simulateInsurance] Simulation completed for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
            return simulation;

        } catch (Exception e) {
            log.error("[simulateInsurance] Error during insurance simulation for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
            throw e;
        } finally {
            log.info("[simulateInsurance] Finished insurance simulation for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
        }
    }

    private ClientInsuranceEntity getClientInsurance(RequestClientInsuranceDTO request, InsuranceStatus status) {
        ClientDTO clientDTO = clientService.getClientById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(request.getClientId()));
        String cpf = clientDTO.getCpf();

        InsuranceEntity insurance = insuranceDataService.findById(request.getInsuranceId())
                .orElseThrow(() -> new InsuranceNotFoundException(request.getInsuranceId()));

        return ClientInsuranceEntity.builder()
                .clientId(request.getClientId())
                .cpf(cpf)
                .insurance(insurance)
                .monthlyCost(insurance.getMonthlyCost())
                .durationMonths(request.getDurationMonths())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(request.getDurationMonths()))
                .status(status)
                .build();
    }

    @Override
    public ClientInsuranceEntity purchaseInsurance(RequestClientInsuranceDTO request) {
        log.info("[purchaseInsurance] Initiating insurance purchase for clientId: {}, insuranceId: {}",
                request.getClientId(), request.getInsuranceId());
        try {
            final boolean hasClientInsurance = clientInsuranceDataService.existsByClientId(request.getClientId());
            if (hasClientInsurance) {
                throw new ClientInsuranceAlreadyExistsException(request.getClientId());
            }
            ClientInsuranceEntity contract = getClientInsurance(request, InsuranceStatus.ACTIVE);
            contract.setId(UUID.randomUUID());
            ClientInsuranceEntity savedContract = clientInsuranceDataService.create(contract);
            log.info("[purchaseInsurance] Successfully purchased insurance for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
            return savedContract;

        } catch (Exception e) {
            log.error("[purchaseInsurance] Error during insurance purchase for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
            throw e;
        } finally {
            log.info("[purchaseInsurance] Finished insurance purchase for clientId: {}, insuranceId: {}",
                    request.getClientId(), request.getInsuranceId());
        }
    }

    @Override
    public void deleteClientInsurance(UUID clientId, UUID clientInsuranceId) {
        log.info("[deleteClientInsurance] Deleting insurance for clientId: {}, clientInsuranceId: {}",
                clientId, clientInsuranceId);
        try {
            clientInsuranceDataService.deleteByIdAndClientId(clientInsuranceId, clientId);
            log.info("[deleteClientInsurance] Successfully deleted insurance for clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
        } catch (Exception e) {
            log.error("[deleteClientInsurance] Error deleting insurance for clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
            throw e;
        } finally {
            log.info("[deleteClientInsurance] Finished deleting insurance for clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
        }
    }

    @Override
    public Optional<ClientInsuranceEntity> getByClientId(UUID clientId) {
        log.info("[getClientInsuranceByClientId] Retrieving insurance for clientId: {}", clientId);
        try {
            Optional<ClientInsuranceEntity> clientInsurance = clientInsuranceDataService.findByClientId(clientId);
            log.info("[getClientInsuranceByClientId] Retrieved insurance for clientId: {}", clientId);
            return clientInsurance;
        } catch (Exception e) {
            log.error("[getClientInsuranceByClientId] Error retrieving insurance for clientId: {}", clientId);
            throw e;
        } finally {
            log.info("[getClientInsuranceByClientId] Finished retrieving insurance for clientId: {}", clientId);
        }
    }
}
