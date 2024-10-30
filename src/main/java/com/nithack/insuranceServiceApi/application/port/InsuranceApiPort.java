package com.nithack.insuranceServiceApi.application.port;

import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;


@Tag(name = "Insurance", description = "API para gerenciamento de seguros")
public interface InsuranceApiPort {


    @Operation(summary = "Cria um novo plano de seguro")
    @ApiResponse(responseCode = "201", description = "Seguro criado com sucesso",
            content = @Content(schema = @Schema(implementation = InsuranceDTO.class)))
    ResponseEntity<InsuranceDTO> createInsurance(@RequestBody(description = "Dados do seguro a ser criado")
                                                 @Valid @RequestBody InsuranceDTO insuranceDTO);

    @Operation(summary = "Lista todos os planos de seguros disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de seguros retornada com sucesso")
    ResponseEntity<List<InsuranceDTO>> getAllInsurances();

    @Operation(summary = "Deleta um seguro pelo ID")
    @ApiResponse(responseCode = "204", description = "Seguro excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Seguro não encontrado")
    ResponseEntity<Void> deleteInsurance(@PathVariable UUID insuranceId);

    @Operation(summary = "Simula o custo de um plano de seguro")
    @ApiResponse(responseCode = "200", description = "Simulação de seguro realizada com sucesso",
            content = @Content(schema = @Schema(implementation = SimulationResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Seguro não encontrado")
    ResponseEntity<SimulationResponseDTO> simulateInsurance(@RequestBody(description = "Dados para simulação de seguro")
                                                            @Valid @RequestBody InsuranceRequestDTO request);

    @Operation(summary = "Contrata um seguro para um cliente")
    @ApiResponse(responseCode = "200", description = "Seguro contratado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou seguro não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    ResponseEntity<InsuranceDTO> purchaseInsurance(@RequestBody(description = "Dados para contratação de seguro")
                                                   @Valid @RequestBody InsuranceRequestDTO request);
}