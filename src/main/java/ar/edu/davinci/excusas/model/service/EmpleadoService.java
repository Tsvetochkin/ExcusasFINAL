package ar.edu.davinci.excusas.model.service;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.dto.EmpleadoRequestDTO;
import ar.edu.davinci.excusas.model.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    @Autowired
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    // from the REST endpoint
    public Empleado registrar(EmpleadoRequestDTO dto) {
        Empleado empleado = new Empleado(dto.getNombre(), dto.getEmail(), dto.getNroLegajo());
        return repository.save(empleado);
    }

    // used at startup
    public Empleado guardar(Empleado empleado) {
        return repository.save(empleado);
    }

    public List<Empleado> listarTodos() {
        return repository.findAll();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Empleado> buscarPorLegajo(int nroLegajo) {
        return repository.findByNroLegajo(nroLegajo);
    }
}
