package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.Prontuario;
import ar.edu.davinci.excusas.model.service.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService service;

    @Autowired
    public ProntuarioController(ProntuarioService service) {
        this.service = service;
    }

    // GET /prontuarios → listado completo de prontuarios
    @GetMapping
    public List<Prontuario> getTodos() {
        return service.listarTodos();
    }
}
