package ar.edu.davinci.excusas.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

// body: { "nombre": "Carlos", "email": "carlos@empresa.com", "nroLegajo": 200, "motivos": ["TRIVIAL", "FAMILIAR"] }
public class EncargadoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Positive(message = "El número de legajo debe ser positivo")
    private int nroLegajo;

    @NotEmpty(message = "Debe indicar al menos un motivo que el encargado pueda manejar")
    private List<String> motivos; // which excuse types this handler can deal with

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
    public List<String> getMotivos() { return motivos; }
    public void setMotivos(List<String> motivos) { this.motivos = motivos; }
}
