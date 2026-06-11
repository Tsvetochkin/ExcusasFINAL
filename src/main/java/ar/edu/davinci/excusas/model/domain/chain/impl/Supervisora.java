package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

public class Supervisora extends Encargado {
    public Supervisora(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return excusa.esModerada();
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("Supervisora procesando excusa moderada.");
        excusa.setAceptada(true);
        
        // Логика из ТЗ: если свет -> EDESUR, если семья -> сотруднику
        if (excusa.getClass().getSimpleName().contains("Luz")) {
            excusa.ejecutarAccion((dest, orig, asunto, cuerpo) -> 
                System.out.println("Email enviado a EDESUR@mailfake.com.ar preguntando si es verdad el corte."));
        } else {
            excusa.ejecutarAccion((dest, orig, asunto, cuerpo) -> 
                System.out.println("Email enviado al empleado " + dest + " preguntando si todo está bien."));
        }
    }
}
