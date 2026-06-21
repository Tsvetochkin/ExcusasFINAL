package ar.edu.davinci.excusas.model.dto;

// Lo que el cliente envía para registrar un empleado:
// { "nombre": "Juan", "email": "juan@empresa.com", "nroLegajo": 504 }
public class EmpleadoRequestDTO {
    private String nombre;
    private String email;
    private int nroLegajo;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
}
