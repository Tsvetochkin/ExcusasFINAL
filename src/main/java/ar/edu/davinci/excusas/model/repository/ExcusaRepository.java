package ar.edu.davinci.excusas.model.repository;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcusaRepository extends JpaRepository<Excusa, Long> {

    // Spring generates SQL from the method name — SELECT * FROM excusas WHERE empleado_id = ?
    List<Excusa> findByEmpleadoId(Long empleadoId);

    List<Excusa> findByEmpleadoNroLegajo(int nroLegajo);

    List<Excusa> findByEmpleadoNroLegajoAndFechaBetween(int nroLegajo, LocalDate desde, LocalDate hasta);

    List<Excusa> findByAceptadaFalse();

    List<Excusa> findByMotivo(TipoExcusa motivo);

    List<Excusa> findByAceptadaPor(String aceptadaPor);

    long deleteByFechaBefore(LocalDate fechaLimite);
}
