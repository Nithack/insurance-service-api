package com.nithack.insuranceServiceApi.infra.http.doc;

import com.nithack.insuranceServiceApi.application.dto.InsuranceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@Tag(name = "Insurance", description = "API para gerenciamento de seguros")
public interface InsuranceControllerAPIDoc {


    @Operation(summary = "Cria um novo plano de seguro")
    @ApiResponse(responseCode = "201", description = "Seguro criado com sucesso",
            content = @Content(schema = @Schema(implementation = InsuranceDTO.class)))
    ResponseEntity<InsuranceDTO> createInsurance(InsuranceDTO insuranceDTO);

    @Operation(summary = "Lista todos os planos de seguros disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de seguros retornada com sucesso")
    ResponseEntity<List<InsuranceDTO>> getAllInsurances();

    @Operation(summary = "Deleta um seguro pelo ID")
    @ApiResponse(responseCode = "204", description = "Seguro excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Seguro não encontrado")
    ResponseEntity<Void> deleteInsurance(UUID insuranceId);

}