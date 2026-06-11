package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.Prontuario;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.observer.AdministradorProntuarios;
import ar.edu.davinci.excusas.model.domain.observer.IObservador;

public class CEO extends Encargado implements IObservador {

    public CEO(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return excusa.esInverosimil();
    }

    @Override
    protected void procesar(Excusa excusa) {
        System.out.println("CEO procesando excusa inverosímil.");
        excusa.setAceptada(true);
        
        // Текст из ТЗ
        excusa.ejecutarAccion((dest, orig, asunto, cuerpo) -> 
            System.out.println("Email enviado a " + dest + " | Respuesta: Aprobado por creatividad"));
        
        Prontuario prontuario = new Prontuario(excusa.getEmpleado(), excusa);
        AdministradorProntuarios.getInstancia().registrarProntuario(prontuario);
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("CEO notificado: nuevo registro en prontuario.");
    }
}
