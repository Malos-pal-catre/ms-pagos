package com.pesquera.pagos.dto;

import com.pesquera.pagos.model.Pago;

public class PagoMapper {

    public static PagoResponseDTO toDTO(Pago pago) {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(pago.getId());
        dto.setSubastaId(pago.getSubastaId());
        dto.setPescadorId(pago.getPescadorId());
        dto.setCompradorId(pago.getCompradorId());
        dto.setEspecie(pago.getEspecie());
        dto.setKilos(pago.getKilos());
        dto.setPrecioFinal(pago.getPrecioFinal());
        dto.setPorcentajeCaleta(pago.getPorcentajeCaleta());
        dto.setMontoCaleta(pago.getMontoCaleta());
        dto.setMontoNeto(pago.getMontoNeto());
        dto.setEstado(pago.getEstado().name());
        dto.setFechaCreacion(pago.getFechaCreacion());
        dto.setFechaPago(pago.getFechaPago());
        return dto;
    }

    public static Pago toEntity(PagoRequestDTO dto) {
        Pago pago = new Pago();
        pago.setSubastaId(dto.getSubastaId());
        pago.setPescadorId(dto.getPescadorId());
        pago.setCompradorId(dto.getCompradorId());
        pago.setEspecie(dto.getEspecie());
        pago.setKilos(dto.getKilos());
        pago.setPrecioFinal(dto.getPrecioFinal());
        return pago;
    }
}