package ar.edu.davinci.excusas.model.service;

import ar.edu.davinci.excusas.model.domain.Prontuario;
import ar.edu.davinci.excusas.model.repository.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository repository;

    @Autowired
    public ProntuarioService(ProntuarioRepository repository) {
        this.repository = repository;
    }

    public List<Prontuario> listarTodos() {
        return repository.findAll();
    }
}
