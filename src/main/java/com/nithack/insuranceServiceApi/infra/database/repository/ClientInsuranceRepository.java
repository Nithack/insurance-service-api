package com.nithack.insuranceServiceApi.infra.database.repository;


import com.nithack.insuranceServiceApi.infra.database.model.ClientInsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientInsuranceRepository extends JpaRepository<ClientInsuranceModel, UUID> {

}
