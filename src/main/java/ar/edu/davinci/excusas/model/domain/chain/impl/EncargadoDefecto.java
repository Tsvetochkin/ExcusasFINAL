package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

public class EncargadoDefecto extends Encargado {
    public EncargadoDefecto(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return true;
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("Encargado Defecto: Rechazada.");
        excusa.setAceptada(false);
    }
}
