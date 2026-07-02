package ar.edu.davinci.excusas.model.domain.excusas;

import jakarta.persistence.Entity;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

@Entity
public class ExcusaLuz extends ExcusaModerada {

    protected ExcusaLuz() {}

    public ExcusaLuz(Empleado empleado) {
        super(empleado, TipoExcusa.LUZ);
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
