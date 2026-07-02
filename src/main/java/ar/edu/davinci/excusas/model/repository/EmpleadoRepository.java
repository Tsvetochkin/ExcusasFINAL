package ar.edu.davinci.excusas.model.repository;

import ar.edu.davinci.excusas.model.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Spring Data generates the SQL from the method name — no query needed
    Empleado findByEmail(String email);

    java.util.List<Empleado> findByNombreContaining(String partOfName);

    java.util.Optional<Empleado> findByNroLegajo(int nroLegajo);
}
