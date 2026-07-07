package ar.edu.davinci.excusas.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

// body: { "legajo": 501, "motivo": "TRIVIAL" }
public class ExcusaRequestDTO {

    @Positive(message = "El legajo debe ser un número positivo")
    private int legajo;

    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo; // String — converted to enum in the service

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
