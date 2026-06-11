package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Базовый класс для умеренных оправданий.
 */
public abstract class ExcusaModerada extends Excusa {

    public ExcusaModerada(Empleado empleado) {
        super(empleado);
    }

    @Override
    public boolean esModerada() {
        return true;
    }
}
