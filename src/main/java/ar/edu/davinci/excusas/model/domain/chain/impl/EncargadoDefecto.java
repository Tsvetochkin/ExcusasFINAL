package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

public class EncargadoDefecto extends Encargado {
    public EncargadoDefecto(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        // Он берет на себя всё, что не обработали другие
        return true;
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("Encargado por Defecto (" + getNombre() + "): La excusa no cumple con los requisitos mínimos. RECHAZADA.");
        excusa.setAceptada(false);
    }

    @Override
    public void derivar(Excusa excusa) {
        // Он не должен ничего деривировать (перекрываем метод родителя)
        System.out.println("Fin de la cadena: No hay más responsables.");
    }
}
