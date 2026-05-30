package com.pesquera.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagoRequestDTO {

    @NotNull(message = "El id de la subasta es obligatorio")
    private Long subastaId;

    @NotNull(message = "El id del pescador es obligatorio")
    private Long pescadorId;

    @NotNull(message = "El id del comprador es obligatorio")
    private Long compradorId;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotNull(message = "Los kilos son obligatorios")
    @Positive(message = "Los kilos deben ser mayor a 0")
    private Double kilos;

    @NotNull(message = "El precio final es obligatorio")
    @Positive(message = "El precio final debe ser mayor a 0")
    private Double precioFinal;
}