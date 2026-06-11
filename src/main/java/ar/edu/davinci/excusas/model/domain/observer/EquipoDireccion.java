package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;

public class EquipoDireccion implements IObservador {
    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("Equipo de Dirección notificado: El empleado " + 
            prontuario.getEmpleado().getNombre() + 
            " (Legajo: " + prontuario.getNroLegajo() + ") presentó una excusa.");
    }
}
