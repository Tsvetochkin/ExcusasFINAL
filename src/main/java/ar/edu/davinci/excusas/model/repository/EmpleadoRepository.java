package ar.edu.davinci.excusas.model.repository;

import ar.edu.davinci.excusas.model.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Слой Repository для Сотрудников.
 * Наследуя JpaRepository, мы получаем все базовые операции CRUD автоматически.
 * 
 * Параметры <Empleado, Long> означают:
 * 1. Empleado - с каким классом работаем.
 * 2. Long - какой тип данных у поля @Id в этом классе.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    /**
     * Пример "магии" Spring Data JPA:
     * Просто объявив метод с таким именем, Spring сам создаст SQL запрос:
     * SELECT * FROM empleados WHERE email = ?
     */
    Empleado findByEmail(String email);

    /**
     * Еще один пример: найти по имени
     */
    java.util.List<Empleado> findByNombreContaining(String partOfName);
}
