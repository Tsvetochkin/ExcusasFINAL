package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Сложное оправдание. Требует внимания отдела кадров (GerenteRRHH).
 */
public class ExcusaCompleja extends Excusa {

    public ExcusaCompleja(Empleado empleado) {
        super(empleado);
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
