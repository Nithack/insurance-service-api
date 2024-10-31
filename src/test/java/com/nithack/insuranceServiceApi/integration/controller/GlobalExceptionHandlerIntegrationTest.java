package com.nithack.insuranceServiceApi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import com.nithack.insuranceServiceApi.application.dto.ClientDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.exception.ClientNotFoundException;
import com.nithack.insuranceServiceApi.application.port.ClientServicePort;
import com.nithack.insuranceServiceApi.infra.database.repository.ClientInsuranceRepository;
import com.nithack.insuranceServiceApi.infra.database.repository.InsuranceRepository;
import static com.nithack.insuranceServiceApi.integration.controller.InsuranceControllerIntegrationTest.getInsuranceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.Optional;
import java.util.UUID;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientInsuranceRepository mockClientInsuranceRepository;
    @MockBean
    private ClientServicePort mockClientService;
    @MockBean
    private InsuranceRepository mockInsuranceRepository;

    @BeforeEach
    void setUp() {
        mockClientInsuranceRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return 404 Not Found when ClientNotFoundException is thrown")
    void shouldReturnNotFoundWhenClientNotFound() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();
        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        when(mockClientService.getClientById(eq(clientId))).thenReturn(Optional.empty());

        doThrow(new ClientNotFoundException(clientId)).when(mockClientInsuranceRepository).findById(clientId);

        mockMvc.perform(post("/clients/insurances/simulate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Client not found with id: " + clientId));
    }

    @Test
    @DisplayName("Should return 404 Not Found when InsuranceNotFoundException is thrown")
    void shouldReturnNotFoundWhenInsuranceNotFound() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();
        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        when(mockClientService.getClientById(eq(clientId))).thenReturn(Optional.of(createClientDTO(clientId)));
        when(mockInsuranceRepository.findById(eq(insuranceId))).thenReturn(Optional.empty());

        mockMvc.perform(post("/clients/insurances/purchase")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Insurance not found with id: " + insuranceId));
    }

    @Test
    @DisplayName("Should return 409 Conflict when ClientInsuranceAlreadyExistsException is thrown")
    void shouldReturnConflictWhenClientInsuranceAlreadyExists() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();
        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        when(mockClientInsuranceRepository.existsByClientId(eq(clientId))).thenReturn(true);

        mockMvc.perform(post("/clients/insurances/purchase")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("Client insurance already exists with clientId: " + clientId));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for invalid input")
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        RequestClientInsuranceDTO invalidRequest = new RequestClientInsuranceDTO(); // Empty request

        mockMvc.perform(post("/clients/insurances/simulate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Invalid request body, please check your inputs."));
    }

    @Test
    @DisplayName("Should return 500 Internal Server Error for general exceptions")
    void shouldReturnInternalServerErrorForGeneralException() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID insuranceId = UUID.randomUUID();
        RequestClientInsuranceDTO request = createRequestClientInsuranceDTO(clientId, insuranceId);

        when(mockClientService.getClientById(eq(clientId))).thenReturn(Optional.of(createClientDTO(clientId)));
        when(mockInsuranceRepository.findById(eq(insuranceId))).thenReturn(Optional.of(getInsuranceModel(insuranceId, "Test Plan", 100000.0, 250.0)));
        doThrow(new RuntimeException("Unexpected error")).when(mockClientInsuranceRepository).save(any());

        mockMvc.perform(post("/clients/insurances/purchase")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred. Please try again later."));
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
}
