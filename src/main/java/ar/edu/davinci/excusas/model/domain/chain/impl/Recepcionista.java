package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

public class Recepcionista extends Encargado {
    public Recepcionista(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return excusa.esTrivial();
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("Recepcionista aceptando excusa trivial.");
        excusa.setAceptada(true);
        // Специфическое письмо из ТЗ
        excusa.ejecutarAccion((dest, orig, asunto, cuerpo) -> 
            System.out.println("Email enviado a " + dest + " | Asunto: motivo demora | Cuerpo: la licencia fue aceptada"));
    }
}
