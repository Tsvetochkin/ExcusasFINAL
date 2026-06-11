package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.EmailSender;
import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Тривиальное оправдание. 
 * Принимается автоматически без лишних проверок.
 */
public class ExcusaTrivial extends Excusa {

    public ExcusaTrivial(Empleado empleado) {
        super(empleado); // Передаем сотрудника наверх родителю
    }

    @Override
    public boolean esTrivial() {
        return true;
    }

    @Override
    public void ejecutarAccion(EmailSender emailSender) {
        // Логика: Тривиальное оправдание просто уведомляет по почте
        emailSender.enviarEmail(
            getEmpleado().getEmail(), 
            "sistema@empresa.com", 
            "Excusa Trivial Aceptada", 
            "Tu excusa ha sido procesada automáticamente."
        );
    }
}
