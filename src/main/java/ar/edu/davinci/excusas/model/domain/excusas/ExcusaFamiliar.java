package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;

/**
 * Оправдание: Семейные обстоятельства.
 */
public class ExcusaFamiliar extends ExcusaModerada {

    public ExcusaFamiliar(Empleado empleado) {
        super(empleado);
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
