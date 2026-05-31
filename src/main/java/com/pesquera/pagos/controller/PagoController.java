package com.pesquera.pagos.controller;

import com.pesquera.pagos.dto.PagoMapper;
import com.pesquera.pagos.dto.PagoRequestDTO;
import com.pesquera.pagos.dto.PagoResponseDTO;
import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(@Valid @RequestBody PagoRequestDTO dto) {
        Pago pago = PagoMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PagoMapper.toDTO(pagoService.crearPago(pago)));
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos().stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.obtenerPorId(id)));
    }

    @GetMapping("/pescador/{pescadorId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorPescador(@PathVariable Long pescadorId) {
        return ResponseEntity.ok(pagoService.obtenerPorPescador(pescadorId).stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorComprador(@PathVariable Long compradorId) {
        return ResponseEntity.ok(pagoService.obtenerPorComprador(compradorId).stream().map(PagoMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PagoResponseDTO> confirmarPago(@PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.confirmarPago(id)));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<PagoResponseDTO> anularPago(@PathVariable Long id) {
        return ResponseEntity.ok(PagoMapper.toDTO(pagoService.anularPago(id)));
    }
}