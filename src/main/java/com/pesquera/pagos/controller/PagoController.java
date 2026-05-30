package com.pesquera.pagos.controller;

import com.pesquera.pagos.dto.PagoRequestDTO;
import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Pago> crearPago(@Valid @RequestBody PagoRequestDTO dto) {
        Pago pago = new Pago();
        pago.setSubastaId(dto.getSubastaId());
        pago.setPescadorId(dto.getPescadorId());
        pago.setCompradorId(dto.getCompradorId());
        pago.setEspecie(dto.getEspecie());
        pago.setKilos(dto.getKilos());
        pago.setPrecioFinal(dto.getPrecioFinal());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crearPago(pago));
    }

    @GetMapping
    public ResponseEntity<List<Pago>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @GetMapping("/pescador/{pescadorId}")
    public ResponseEntity<List<Pago>> obtenerPorPescador(@PathVariable Long pescadorId) {
        return ResponseEntity.ok(pagoService.obtenerPorPescador(pescadorId));
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<Pago>> obtenerPorComprador(@PathVariable Long compradorId) {
        return ResponseEntity.ok(pagoService.obtenerPorComprador(compradorId));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Pago> confirmarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.confirmarPago(id));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<Pago> anularPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.anularPago(id));
    }
}