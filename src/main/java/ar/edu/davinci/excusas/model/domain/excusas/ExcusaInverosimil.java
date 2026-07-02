package ar.edu.davinci.excusas.model.domain.excusas;

import jakarta.persistence.Entity;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

@Entity
public class ExcusaInverosimil extends Excusa {

    protected ExcusaInverosimil() {}

    public ExcusaInverosimil(Empleado empleado) {
        super(empleado, TipoExcusa.INVEROSIMIL);
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
