package ar.edu.davinci.excusas.model.domain.excusas;

import jakarta.persistence.Entity;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

@Entity
public class ExcusaCompleja extends Excusa {

    protected ExcusaCompleja() {}

    public ExcusaCompleja(Empleado empleado) {
        super(empleado, TipoExcusa.COMPLEJA);
    }

    @Override
    public boolean esCompleja() {
        return true;
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        emailSender.enviarEmail(
            getEmpleado().getEmail(),
            "gerencia@empresa.com",
            "Análisis de Excusa Compleja",
            "Tu caso ha sido escalado a la gerencia de RRHH para un análisis detallado."
        );
    }
}
