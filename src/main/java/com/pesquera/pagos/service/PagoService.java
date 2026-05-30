package com.pesquera.pagos.service;

import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.model.EstadoPago;
import com.pesquera.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    private static final double PORCENTAJE_CALETA = 0.08;

    public Pago crearPago(Pago pago) {
        pago.setPorcentajeCaleta(PORCENTAJE_CALETA * 100);
        pago.setMontoCaleta(pago.getPrecioFinal() * pago.getKilos() * PORCENTAJE_CALETA);
        pago.setMontoNeto((pago.getPrecioFinal() * pago.getKilos()) - pago.getMontoCaleta());
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setFechaCreacion(LocalDateTime.now());
        return pagoRepository.save(pago);
    }

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
    }

    public List<Pago> obtenerPorPescador(Long pescadorId) {
        return pagoRepository.findByPescadorId(pescadorId);
    }

    public List<Pago> obtenerPorComprador(Long compradorId) {
        return pagoRepository.findByCompradorId(compradorId);
    }

    public Pago confirmarPago(Long id) {
        Pago pago = obtenerPorId(id);
        pago.setEstado(EstadoPago.PAGADO);
        pago.setFechaPago(LocalDateTime.now());
        return pagoRepository.save(pago);
    }

    public Pago anularPago(Long id) {
        Pago pago = obtenerPorId(id);
        pago.setEstado(EstadoPago.ANULADO);
        return pagoRepository.save(pago);
    }
}