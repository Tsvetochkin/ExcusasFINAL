package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.dto.EmpleadoRequestDTO;
import ar.edu.davinci.excusas.model.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    @Autowired
    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    // GET /empleados
    @GetMapping
    public List<Empleado> getTodos() {
        return service.listarTodos();
    }

    // POST /empleados — body: { "nombre": "Juan", "email": "juan@empresa.com", "nroLegajo": 504 }
    @PostMapping
    public ResponseEntity<Empleado> registrar(@RequestBody EmpleadoRequestDTO dto) {
        Empleado creado = service.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
