package com.pesquera.pagos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagoResponseDTO {
    private Long id;
    private Long subastaId;
    private Long pescadorId;
    private Long compradorId;
    private String especie;
    private Double kilos;
    private Double precioFinal;
    private Double porcentajeCaleta;
    private Double montoCaleta;
    private Double montoNeto;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaPago;
}