package ar.edu.davinci.excusas.model.service;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Слой Service для сотрудников.
 * Здесь живет "бизнес-логика". 
 */
@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    /**
     * Используем инъекцию через конструктор, как рекомендовано в уроке (стр. 14).
     */
    @Autowired
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод для сохранения сотрудника с проверкой.
     */
    public Empleado guardar(Empleado empleado) {
        // Здесь могла бы быть логика: например, проверка формата email
        System.out.println("Service: Обработка сохранения сотрудника " + empleado.getNombre());
        return repository.save(empleado);
    }

    /**
     * Получить всех сотрудников из базы.
     */
    public List<Empleado> listarTodos() {
        return repository.findAll();
    }

    /**
     * Найти конкретного сотрудника по ID.
     */
    public Optional<Empleado> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
