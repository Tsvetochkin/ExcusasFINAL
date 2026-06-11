package ar.edu.davinci.excusas.model.domain;

public class Prontuario {
    private int nroLegajo;
    private Empleado empleado;
    private Excusa excusa;

    public Prontuario(Empleado empleado, Excusa excusa) {
        this.empleado = empleado;
        this.excusa = excusa;
        this.nroLegajo = empleado.getNroLegajo();
    }

    public int getNroLegajo() {
        return nroLegajo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Excusa getExcusa() {
        return excusa;
    }
}
