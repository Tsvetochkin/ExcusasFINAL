package ar.edu.davinci.excusas.model.repository;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcusaRepository extends JpaRepository<Excusa, Long> {

    // Spring genera el SQL automáticamente por el nombre del método:
    // SELECT * FROM excusas WHERE empleado_id = ?
    List<Excusa> findByEmpleadoId(Long empleadoId);

    // SELECT * FROM excusas WHERE empleado.nro_legajo = ?
    List<Excusa> findByEmpleadoNroLegajo(int nroLegajo);

    // Buscar por legajo y rango de fechas
    List<Excusa> findByEmpleadoNroLegajoAndFechaBetween(int nroLegajo, LocalDate desde, LocalDate hasta);

    // Todas las rechazadas (aceptada = false)
    List<Excusa> findByAceptadaFalse();

    // Filtrar por motivo (tipo)
    List<Excusa> findByMotivo(TipoExcusa motivo);

    // Filtrar por encargado que aceptó
    List<Excusa> findByAceptadaPor(String aceptadaPor);

    // Eliminar las anteriores a una fecha (devuelve cuántas eliminó)
    long deleteByFechaBefore(LocalDate fechaLimite);
}
