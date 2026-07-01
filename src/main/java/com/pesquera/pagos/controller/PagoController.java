package com.pesquera.pagos.controller;

import com.pesquera.pagos.dto.PagoMapper;
import com.pesquera.pagos.dto.PagoRequestDTO;
import com.pesquera.pagos.dto.PagoResponseDTO;
import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestión de pagos - Caleta Pesquera")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(
            summary = "Crear un nuevo pago",
            description = "Genera un pago a partir de una subasta adjudicada, calculando el porcentaje de la caleta y el monto neto para el pescador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagoCreado",
                                    value = "{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PENDIENTE\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":null}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorValidacion",
                                    value = "{\"precioFinal\":\"El precio final debe ser mayor a 0\"}"
                            )))
    })
    public ResponseEntity<PagoResponseDTO> crearPago(
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a generar",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "NuevoPago",
                                    value = "{\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0}"
                            )))
            @Valid PagoRequestDTO dto) {
        Pago pago = PagoMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PagoMapper.toDTO(pagoService.crearPago(pago)));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los pagos",
            description = "Retorna el listado completo de pagos registrados, en cualquier estado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ListadoPagos",
                                    value = "[{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PENDIENTE\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":null}]"
                            )))
    })
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos().stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pago por ID",
            description = "Retorna los datos de un pago específico según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagoEncontrado",
                                    value = "{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PENDIENTE\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":null}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.obtenerPorId(id)));
    }

    @GetMapping("/pescador/{pescadorId}")
    @Operation(
            summary = "Listar pagos por pescador",
            description = "Retorna todos los pagos asociados a un pescador específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pagos del pescador",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagosPorPescador",
                                    value = "[{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PAGADO\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":\"2026-06-30T11:00:00\"}]"
                            )))
    })
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorPescador(
            @Parameter(description = "ID del pescador", example = "2")
            @PathVariable Long pescadorId) {
        return ResponseEntity.ok(pagoService.obtenerPorPescador(pescadorId).stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/comprador/{compradorId}")
    @Operation(
            summary = "Listar pagos por comprador",
            description = "Retorna todos los pagos asociados a un comprador específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pagos del comprador",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagosPorComprador",
                                    value = "[{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PAGADO\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":\"2026-06-30T11:00:00\"}]"
                            )))
    })
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorComprador(
            @Parameter(description = "ID del comprador", example = "3")
            @PathVariable Long compradorId) {
        return ResponseEntity.ok(pagoService.obtenerPorComprador(compradorId).stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/confirmar")
    @Operation(
            summary = "Confirmar un pago",
            description = "Marca un pago como pagado, registrando la fecha de pago."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago confirmado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagoConfirmado",
                                    value = "{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"PAGADO\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":\"2026-06-30T11:00:00\"}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponseDTO> confirmarPago(
            @Parameter(description = "ID del pago a confirmar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.confirmarPago(id)));
    }

    @PutMapping("/{id}/anular")
    @Operation(
            summary = "Anular un pago",
            description = "Marca un pago como anulado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago anulado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PagoAnulado",
                                    value = "{\"id\":1,\"subastaId\":10,\"pescadorId\":2,\"compradorId\":3,\"especie\":\"Loco\",\"kilos\":120.5,\"precioFinal\":850000.0,\"porcentajeCaleta\":0.1,\"montoCaleta\":85000.0,\"montoNeto\":765000.0,\"estado\":\"ANULADO\",\"fechaCreacion\":\"2026-06-30T10:30:00\",\"fechaPago\":null}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponseDTO> anularPago(
            @Parameter(description = "ID del pago a anular", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.anularPago(id)));
    }
}