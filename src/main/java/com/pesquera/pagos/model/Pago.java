package com.pesquera.pagos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long subastaId;

    @Column(nullable = false)
    private Long pescadorId;

    @Column(nullable = false)
    private Long compradorId;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private Double kilos;

    @Column(nullable = false)
    private Double precioFinal;

    @Column(nullable = false)
    private Double porcentajeCaleta;

    @Column(nullable = false)
    private Double montoCaleta;

    @Column(nullable = false)
    private Double montoNeto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaPago;
}