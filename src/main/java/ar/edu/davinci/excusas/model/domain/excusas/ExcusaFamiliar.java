package ar.edu.davinci.excusas.model.domain.excusas;

import jakarta.persistence.Entity;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

/**
 * Оправдание: Семейные обстоятельства.
 */
@Entity
public class ExcusaFamiliar extends ExcusaModerada {

    protected ExcusaFamiliar() {}

    public ExcusaFamiliar(Empleado empleado) {
        super(empleado, TipoExcusa.FAMILIAR);
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        emailSender.enviarEmail(
            getEmpleado().getEmail(),
            "rrhh@empresa.com",
            "Licencia por motivos familiares",
            "Se ha procesado tu solicitud por temas familiares. ¡Esperamos que todo se solucione pronto!"
        );
    }
}
