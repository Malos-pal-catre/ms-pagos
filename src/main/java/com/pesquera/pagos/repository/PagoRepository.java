package com.pesquera.pagos.repository;

import com.pesquera.pagos.model.Pago;
import com.pesquera.pagos.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPescadorId(Long pescadorId);
    List<Pago> findByCompradorId(Long compradorId);
    List<Pago> findByEstado(EstadoPago estado);
    List<Pago> findBySubastaId(Long subastaId);

    @Query("SELECT p FROM Pago p WHERE p.pescadorId = :pescadorId AND p.estado = 'PAGADO'")
    List<Pago> findPagosPagadosByPescador(@Param("pescadorId") Long pescadorId);

    @Query("SELECT p FROM Pago p WHERE p.especie = :especie ORDER BY p.fechaCreacion DESC")
    List<Pago> findPagosByEspecie(@Param("especie") String especie);

    @Query(value = "SELECT * FROM pagos WHERE estado = 'PENDIENTE' ORDER BY fecha_creacion ASC", nativeQuery = true)
    List<Pago> findTodosPagosPendientes();
}