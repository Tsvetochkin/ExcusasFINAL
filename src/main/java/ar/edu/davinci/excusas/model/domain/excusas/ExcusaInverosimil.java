package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Невероятное оправдание. Только CEO может решить судьбу такого сотрудника.
 */
public class ExcusaInverosimil extends Excusa {

    public ExcusaInverosimil(Empleado empleado) {
        super(empleado);
    }

    @Override
    public boolean esInverosimil() {
        return true;
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        emailSender.enviarEmail(
            getEmpleado().getEmail(),
            "ceo@empresa.com",
            "Notificación de Caso Especial",
            "Tu excusa es... peculiar. El CEO revisará tu situación personalmente."
        );
    }
}
