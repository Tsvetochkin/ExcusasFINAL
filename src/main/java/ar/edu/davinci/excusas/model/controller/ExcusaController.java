package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.Excusa;

// entry point for the chain of responsibility
public interface ExcusaController {
    void manejar(Excusa excusa);
}
