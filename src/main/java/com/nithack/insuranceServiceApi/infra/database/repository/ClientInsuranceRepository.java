package com.nithack.insuranceServiceApi.infra.database.repository;


import com.nithack.insuranceServiceApi.infra.database.model.ClientInsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientInsuranceRepository extends JpaRepository<ClientInsuranceModel, UUID> {
    Optional<ClientInsuranceModel> findByClientId(UUID clientId);
    boolean existsByClientId(UUID clientId);
    void deleteByIdAndClientId(UUID clientInsuranceId, UUID clientId);
}
