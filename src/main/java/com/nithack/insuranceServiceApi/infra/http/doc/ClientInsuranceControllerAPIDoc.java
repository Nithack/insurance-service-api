package com.nithack.insuranceServiceApi.infra.http.doc;

import com.nithack.insuranceServiceApi.application.dto.ClientInsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import com.nithack.insuranceServiceApi.application.dto.RequestClientInsuranceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@Tag(name = "Insurance", description = "API para gerenciamento de seguros")
public interface ClientInsuranceControllerAPIDoc {

    @Operation(summary = "Simula o custo de um plano de seguro")
    @ApiResponse(responseCode = "200", description = "Simulação de seguro realizada com sucesso",
            content = @Content(schema = @Schema(implementation = RequestClientInsuranceDTO.class)))
    @ApiResponse(responseCode = "404", description = "Seguro não encontrado")
    ResponseEntity<ClientInsuranceDTO> simulateInsurance(RequestClientInsuranceDTO request);

    @Operation(summary = "Contrata um seguro para um cliente")
    @ApiResponse(responseCode = "200", description = "Seguro contratado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou seguro não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    ResponseEntity<ClientInsuranceDTO> purchaseInsurance(RequestClientInsuranceDTO request);

    @Operation(summary = "Exclui o seguro de um cliente")
    @ApiResponse(responseCode = "204", description = "Seguro excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente ou seguro não encontrado")
    ResponseEntity<Void> deleteClientInsurance(UUID clientId, UUID clientInsuranceId);

    @Operation(summary = "Obtém o seguro de um cliente pelo clientId")
    @ApiResponse(responseCode = "200", description = "Seguro do cliente encontrado",
            content = @Content(schema = @Schema(implementation = ClientInsuranceDTO.class)))
    @ApiResponse(responseCode = "404", description = "Cliente ou seguro não encontrado")
    ResponseEntity<ClientInsuranceDTO> getClientInsurance(UUID clientId);
}