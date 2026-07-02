package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

// kicks off the chain of responsibility starting from the first handler
public class CadenaDeEncargados implements ExcusaController {
    private Encargado primero;

    public CadenaDeEncargados(Encargado primero) {
        this.primero = primero;
    }

    @Override
    public void manejar(Excusa excusa) {
        if (primero != null) {
            primero.manejar(excusa);
        } else {
            System.out.println("Error: No hay una cadena configurada.");
        }
    }
}
