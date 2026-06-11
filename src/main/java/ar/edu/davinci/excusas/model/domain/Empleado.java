package ar.edu.davinci.excusas.model.domain;

public class Empleado {
    private String nombre;
    private String email;
    private int nroLegajo;

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }
}
