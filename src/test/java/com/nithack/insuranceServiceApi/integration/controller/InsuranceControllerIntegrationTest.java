package com.nithack.insuranceServiceApi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nithack.insuranceServiceApi.TestcontainersConfiguration;
import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import com.nithack.insuranceServiceApi.infra.database.model.InsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.repository.ClientInsuranceRepository;
import com.nithack.insuranceServiceApi.infra.database.repository.InsuranceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InsuranceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private ClientInsuranceRepository clientInsuranceRepository;

    static InsuranceModel getInsuranceModel(UUID id, String name, double coverageAmount, double monthlyCost) {
        return InsuranceModel.builder()
                .id(id)
                .name(name)
                .coverageAmount(coverageAmount)
                .monthlyCost(monthlyCost)
                .benefits(List.of("Benefit A", "Benefit B"))
                .build();
    }

    @BeforeEach
    void setUp() {
        clientInsuranceRepository.deleteAll();
        insuranceRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new insurance plan")
    void shouldCreateNewInsurance() throws Exception {
        InsuranceDTO insuranceDTO = createInsuranceDTO();

        mockMvc.perform(post("/insurance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(insuranceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(insuranceDTO.getName()))
                .andExpect(jsonPath("$.coverageAmount").value(insuranceDTO.getCoverageAmount()))
                .andExpect(jsonPath("$.monthlyCost").value(insuranceDTO.getMonthlyCost()));
    }

    @Test
    @DisplayName("Should return all insurance plans")
    void shouldReturnAllInsurances() throws Exception {
        InsuranceModel insurance1 = getInsuranceModel(UUID.randomUUID(), "Plan A", 100000, 200.0);
        InsuranceModel insurance2 = getInsuranceModel(UUID.randomUUID(), "Plan B", 50000, 150.0);

        insuranceRepository.saveAll(List.of(insurance1, insurance2));

        mockMvc.perform(get("/insurance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should delete insurance by ID")
    void shouldDeleteInsuranceById() throws Exception {
        UUID insuranceId = UUID.randomUUID();
        InsuranceModel insuranceModel = getInsuranceModel(insuranceId, "Plan to Delete", 75000, 180.0);

        insuranceRepository.save(insuranceModel);

        mockMvc.perform(delete("/insurance/{insuranceId}", insuranceId))
                .andExpect(status().isNoContent());

        var foundInsurance = insuranceRepository.findById(insuranceId);
        assertThat(foundInsurance).isEmpty();
    }

    @Test
    @DisplayName("Should return 404 when insurance is not found for deletion")
    void shouldReturn404WhenInsuranceNotFoundForDeletion() throws Exception {
        final UUID insuranceId = UUID.randomUUID();
        mockMvc.perform(delete("/insurance/{insuranceId}", insuranceId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Insurance not found with id: " + insuranceId));
    }

    @Test
    @DisplayName("Should handle invalid data input and return 400 Bad Request")
    void shouldReturn400WhenInvalidDataProvided() throws Exception {
        InsuranceDTO invalidInsuranceDTO = InsuranceDTO.builder()
                .name("")  // Invalid: empty name
                .coverageAmount(-50000.0)  // Invalid: negative coverage
                .monthlyCost(0.0)  // Invalid: non-positive cost
                .benefits(List.of(""))  // Invalid: empty benefit
                .build();

        mockMvc.perform(post("/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInsuranceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid request body, please check your inputs."));
    }

    private InsuranceDTO createInsuranceDTO() {
        return InsuranceDTO.builder()
                .name("Test Plan")
                .coverageAmount(100000.0)
                .monthlyCost(250.0)
                .benefits(List.of("Benefit A", "Benefit B"))
                .build();
    }
}