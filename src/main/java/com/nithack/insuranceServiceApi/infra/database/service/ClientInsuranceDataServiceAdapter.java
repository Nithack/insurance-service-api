package com.nithack.insuranceServiceApi.infra.database.service;

import com.nithack.insuranceServiceApi.application.mapper.ClientInsuranceMapper;
import com.nithack.insuranceServiceApi.application.port.ClientInsuranceDataServicePort;
import com.nithack.insuranceServiceApi.domain.entity.ClientInsuranceEntity;
import com.nithack.insuranceServiceApi.infra.database.model.ClientInsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.repository.ClientInsuranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientInsuranceDataServiceAdapter implements ClientInsuranceDataServicePort {

    private final ClientInsuranceRepository clientInsuranceRepository;

    @Override
    public ClientInsuranceEntity create(ClientInsuranceEntity clientInsurance) {
        log.info("[create] Saving client insurance with clientId: {}", clientInsurance.getClientId());
        try {
            ClientInsuranceModel clientInsuranceModel = ClientInsuranceMapper.toModel(clientInsurance);
            ClientInsuranceModel savedClientInsurance = clientInsuranceRepository.save(clientInsuranceModel);
            log.info("[create] Successfully saved client insurance with id: {}", savedClientInsurance.getId());
            return ClientInsuranceMapper.toEntity(savedClientInsurance);
        } catch (Exception e) {
            log.error("[create] Error saving client insurance with clientId: {}", clientInsurance.getClientId(), e);
            throw e;
        } finally {
            log.info("[create] Finished saving client insurance with clientId: {}", clientInsurance.getClientId());
        }
    }

    @Override
    public ClientInsuranceEntity update(ClientInsuranceEntity clientInsurance) {
        log.info("[update] Updating client insurance with id: {}", clientInsurance.getId());
        try {
            ClientInsuranceModel clientInsuranceModel = ClientInsuranceMapper.toModel(clientInsurance);
            ClientInsuranceModel updatedClientInsurance = clientInsuranceRepository.save(clientInsuranceModel);
            log.info("[update] Successfully updated client insurance with id: {}", updatedClientInsurance.getId());
            return ClientInsuranceMapper.toEntity(updatedClientInsurance);
        } catch (Exception e) {
            log.error("[update] Error updating client insurance with id: {}", clientInsurance.getId(), e);
            throw e;
        } finally {
            log.info("[update] Finished updating client insurance with id: {}", clientInsurance.getId());
        }
    }

    @Override
    public void deleteById(UUID insuranceId) {
        log.info("[deleteById] Deleting client insurance with id: {}", insuranceId);
        try {
            clientInsuranceRepository.deleteById(insuranceId);
            log.info("[deleteById] Successfully deleted client insurance with id: {}", insuranceId);
        } catch (Exception e) {
            log.error("[deleteById] Error deleting client insurance with id: {}", insuranceId, e);
            throw e;
        } finally {
            log.info("[deleteById] Finished deleting client insurance with id: {}", insuranceId);
        }
    }

    @Override
    public Optional<ClientInsuranceEntity> findByClientId(UUID clientId) {
        log.info("[findByClientId] Retrieving client insurance with clientId: {}", clientId);
        try {
            Optional<ClientInsuranceModel> clientInsuranceModel = clientInsuranceRepository.findByClientId(clientId);
            Optional<ClientInsuranceEntity> clientInsuranceEntity = clientInsuranceModel.map(ClientInsuranceMapper::toEntity);
            log.info("[findByClientId] Successfully retrieved client insurance with clientId: {}", clientId);
            return clientInsuranceEntity;
        } catch (Exception e) {
            log.error("[findByClientId] Error retrieving client insurance with clientId: {}", clientId, e);
            throw e;
        } finally {
            log.info("[findByClientId] Finished retrieving client insurance with clientId: {}", clientId);
        }
    }
}