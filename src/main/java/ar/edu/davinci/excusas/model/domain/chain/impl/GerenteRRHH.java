package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

public class GerenteRRHH extends Encargado {
    public GerenteRRHH(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return excusa.esCompleja();
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("Gerente RRHH процессирует.");
        excusa.setAceptada(true);
        excusa.ejecutarAccion((dest, orig, asunto, cuerpo) -> System.out.println("Email enviado."));
    }
}
