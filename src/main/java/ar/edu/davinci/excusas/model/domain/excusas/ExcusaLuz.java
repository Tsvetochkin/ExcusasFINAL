package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;

/**
 * Оправдание: Отключили свет.
 */
public class ExcusaLuz extends ExcusaModerada {

    public ExcusaLuz(Empleado empleado) {
        super(empleado);
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        emailSender.enviarEmail(
            getEmpleado().getEmail(),
            "mantenimiento@empresa.com",
            "Reporte de falta de luz",
            "Se ha registrado tu falta por problemas eléctricos. Por favor, adjunta el comprobante de la compañía eléctrica."
        );
    }
}
