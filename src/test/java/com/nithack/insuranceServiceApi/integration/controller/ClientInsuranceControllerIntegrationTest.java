package com.nithack.insuranceServiceApi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import com.nithack.insuranceServiceApi.TestcontainersConfiguration;
import com.nithack.insuranceServiceApi.application.dto.ClientDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.domain.enums.InsuranceStatus;
import com.nithack.insuranceServiceApi.infra.database.model.ClientInsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.model.InsuranceModel;
import com.nithack.insuranceServiceApi.infra.database.repository.ClientInsuranceRepository;
import com.nithack.insuranceServiceApi.infra.database.repository.InsuranceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Import({TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWireMock(
        @ConfigureWireMock(port = 3333)
)
@ActiveProfiles("test")
class ClientInsuranceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientInsuranceRepository clientInsuranceRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @BeforeEach
    void setUp() {
        clientInsuranceRepository.deleteAll();
        insuranceRepository.deleteAll();
    }

    @Test
    @DisplayName("Should simulate insurance for an existing client")
    void shouldSimulateInsuranceForExistingClient() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();

        stubFor(get(urlPathMatching("/clients/" + clientId))
                .willReturn(okJson(objectMapper.writeValueAsString(createClientDTO(clientId)))));

        var insuranceModel = getInsuranceModel(insuranceId, "New Plan", 200000.0, 450.0);
        insuranceRepository.save(insuranceModel);

        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        mockMvc.perform(post("/clients/insurances/simulate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(clientId.toString()))
                .andExpect(jsonPath("$.status").value("SIMULATED"))
                .andExpect(jsonPath("$.insuranceId").value(insuranceId.toString()));
    }

    @Test
    @DisplayName("Should purchase insurance for an existing client")
    void shouldPurchaseInsuranceForExistingClient() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();

        stubFor(get(urlPathMatching("/clients/" + clientId))
                .willReturn(okJson(objectMapper.writeValueAsString(createClientDTO(clientId)))));

        var insuranceModel = getInsuranceModel(insuranceId, "Test Plan", 100000.0, 250.0);
        insuranceRepository.save(insuranceModel);

        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        mockMvc.perform(post("/clients/insurances/purchase")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(clientId.toString()))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should return 404 when client not found in external service")
    void shouldReturnNotFoundWhenClientNotFoundInExternalService() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();

        // Simula a ausência do cliente no serviço externo
        stubFor(get(urlPathMatching("/clients/" + clientId))
                .willReturn(noContent()));

        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        mockMvc.perform(post("/clients/insurances/simulate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Client not found with id: " + clientId));
    }

    @Test
    @DisplayName("Should return 409 Conflict when client already has insurance")
    void shouldReturnConflictWhenClientAlreadyHasInsurance() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();
        UUID clientInsuranceId = UUID.randomUUID();

        stubFor(get(urlPathMatching("/clients/" + clientId))
                .willReturn(okJson(objectMapper.writeValueAsString(createClientDTO(clientId)))));

        var insuranceModel = getInsuranceModel(insuranceId, "Test Plan", 100000.0, 250.0);
        var clientInsuranceModel = createClientInsuranceModel(clientId);
        clientInsuranceModel.setId(clientInsuranceId);
        clientInsuranceModel.setInsurance(insuranceModel);
        insuranceRepository.save(insuranceModel);
        clientInsuranceRepository.save(clientInsuranceModel);

        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        mockMvc.perform(post("/clients/insurances/simulate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("Client insurance already exists with clientId: " + clientId));
    }

    private RequestClientInsuranceDTO createRequestClientInsuranceDTO(UUID clientId, UUID insuranceId) {
        return RequestClientInsuranceDTO.builder()
                .clientId(clientId)
                .insuranceId(insuranceId)
                .durationMonths(12)
                .build();
    }

    public ClientDTO createClientDTO(UUID clientId) {
        return ClientDTO.builder()
                .id(clientId)
                .cpf("12345678901")
                .build();
    }

    private ClientInsuranceModel createClientInsuranceModel(UUID clientId) {
        return ClientInsuranceModel.builder()
                .clientId(clientId)
                .cpf("12345678901")
                .durationMonths(12)
                .monthlyCost(100.0)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(12))
                .status(InsuranceStatus.ACTIVE.name())
                .build();
    }

    private static InsuranceModel getInsuranceModel(UUID id, String name, double coverageAmount, double monthlyCost) {
        return InsuranceModel.builder()
                .id(id)
                .name(name)
                .coverageAmount(coverageAmount)
                .monthlyCost(monthlyCost)
                .benefits(List.of("Benefit A", "Benefit B"))
                .build();
    }
}