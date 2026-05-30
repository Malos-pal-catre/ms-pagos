package com.pesquera.pagos.repository;

import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPescadorId(Long pescadorId);

    List<Pago> findByCompradorId(Long compradorId);

    List<Pago> findByEstado(EstadoPago estado);

    List<Pago> findBySubastaId(Long subastaId);
}