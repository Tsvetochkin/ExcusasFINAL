package ar.edu.davinci.excusas.model.domain.excusas;

import jakarta.persistence.Entity;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

@Entity
public class ExcusaTrivial extends Excusa {

    protected ExcusaTrivial() {}

    public ExcusaTrivial(Empleado empleado) {
        super(empleado, TipoExcusa.TRIVIAL);
    }

    @Override
    public boolean esTrivial() {
        return true;
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        emailSender.enviarEmail(
            getEmpleado().getEmail(), 
            "sistema@empresa.com", 
            "Excusa Trivial Aceptada", 
            "Tu excusa ha sido procesada automáticamente."
        );
    }
}
