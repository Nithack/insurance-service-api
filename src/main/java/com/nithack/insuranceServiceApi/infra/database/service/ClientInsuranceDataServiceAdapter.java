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
        log.debug("[create] Saving client insurance with clientId: {}", clientInsurance.getClientId());
        try {
            ClientInsuranceModel clientInsuranceModel = ClientInsuranceMapper.toModel(clientInsurance);
            ClientInsuranceModel savedClientInsurance = clientInsuranceRepository.save(clientInsuranceModel);
            log.debug("[create] Successfully saved client insurance with id: {}", savedClientInsurance.getId());
            return ClientInsuranceMapper.toEntity(savedClientInsurance);
        } catch (Exception e) {
            log.error("[create] Error saving client insurance with clientId: {}", clientInsurance.getClientId(), e);
            throw e;
        } finally {
            log.debug("[create] Finished saving client insurance with clientId: {}", clientInsurance.getClientId());
        }
    }

    @Override
    public ClientInsuranceEntity update(ClientInsuranceEntity clientInsurance) {
        log.debug("[update] Updating client insurance with id: {}", clientInsurance.getId());
        try {
            ClientInsuranceModel clientInsuranceModel = ClientInsuranceMapper.toModel(clientInsurance);
            ClientInsuranceModel updatedClientInsurance = clientInsuranceRepository.save(clientInsuranceModel);
            log.debug("[update] Successfully updated client insurance with id: {}", updatedClientInsurance.getId());
            return ClientInsuranceMapper.toEntity(updatedClientInsurance);
        } catch (Exception e) {
            log.error("[update] Error updating client insurance with id: {}", clientInsurance.getId(), e);
            throw e;
        } finally {
            log.debug("[update] Finished updating client insurance with id: {}", clientInsurance.getId());
        }
    }

    @Override
    public void deleteById(UUID insuranceId) {
        log.debug("[deleteById] Deleting client insurance with id: {}", insuranceId);
        try {
            clientInsuranceRepository.deleteById(insuranceId);
            log.debug("[deleteById] Successfully deleted client insurance with id: {}", insuranceId);
        } catch (Exception e) {
            log.error("[deleteById] Error deleting client insurance with id: {}", insuranceId, e);
            throw e;
        } finally {
            log.debug("[deleteById] Finished deleting client insurance with id: {}", insuranceId);
        }
    }

    @Override
    public Optional<ClientInsuranceEntity> findByClientId(UUID clientId) {
        log.debug("[findByClientId] Retrieving client insurance with clientId: {}", clientId);
        try {
            Optional<ClientInsuranceModel> clientInsuranceModel = clientInsuranceRepository.findByClientId(clientId);
            Optional<ClientInsuranceEntity> clientInsuranceEntity = clientInsuranceModel.map(ClientInsuranceMapper::toEntity);
            log.debug("[findByClientId] Successfully retrieved client insurance with clientId: {}", clientId);
            return clientInsuranceEntity;
        } catch (Exception e) {
            log.error("[findByClientId] Error retrieving client insurance with clientId: {}", clientId, e);
            throw e;
        } finally {
            log.info("[findByClientId] Finished retrieving client insurance with clientId: {}", clientId);
        }
    }

    @Override
    public void deleteByIdAndClientId(UUID clientInsuranceId, UUID clientId) {
        log.debug("[deleteByIdAndClientId] Deleting client insurance with clientId: {}, clientInsuranceId: {}",
                clientId, clientInsuranceId);
        try {
            clientInsuranceRepository.deleteByIdAndClientId(clientInsuranceId, clientId);
            log.debug("[deleteByIdAndClientId] Successfully deleted client insurance with clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
        } catch (Exception e) {
            log.error("[deleteByIdAndClientId] Error deleting client insurance with clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
            throw e;
        }finally {
            log.debug("[deleteByIdAndClientId] Finished deleting client insurance with clientId: {}, clientInsuranceId: {}",
                    clientId, clientInsuranceId);
        }
    }

    @Override
    public boolean existsByClientId(UUID clientId) {
        log.debug("[existsByClientId] Verifying if client insurance exists with clientId: {}", clientId);
        try {
            boolean exists = clientInsuranceRepository.existsByClientId(clientId);
            log.debug("[existsByClientId] Successfully verified if client insurance exists with clientId: {}", clientId);
            return exists;
        } catch (Exception e) {
            log.error("[existsByClientId] Error verifying if client insurance exists with clientId: {}", clientId, e);
            throw e;
        } finally {
            log.debug("[existsByClientId] Finished verifying if client insurance exists with clientId: {}", clientId);
        }
    }
}