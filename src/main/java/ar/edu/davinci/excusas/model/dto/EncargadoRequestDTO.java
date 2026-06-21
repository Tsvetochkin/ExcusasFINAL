package ar.edu.davinci.excusas.model.dto;

import java.util.List;

// Para POST /encargados: agregar un nuevo encargado dinámicamente
// { "nombre": "Carlos", "email": "carlos@empresa.com", "nroLegajo": 200, "motivos": ["TRIVIAL", "FAMILIAR"] }
public class EncargadoRequestDTO {
    private String nombre;
    private String email;
    private int nroLegajo;
    private List<String> motivos; // qué tipos de excusa puede manejar

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
    public List<String> getMotivos() { return motivos; }
    public void setMotivos(List<String> motivos) { this.motivos = motivos; }
}
