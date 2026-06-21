package ar.edu.davinci.excusas.model.domain.excusas;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;

public abstract class ExcusaModerada extends Excusa {

    protected ExcusaModerada() {}

    public ExcusaModerada(Empleado empleado, TipoExcusa tipo) {
        super(empleado, tipo);
    }

    @Override
    public boolean esModerada() {
        return true;
    }
}
