package ar.edu.davinci.excusas.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

// body: { "nroLegajo": 101, "modo": "VAGO" } — valid values: NORMAL, VAGO, PRODUCTIVO
public class ModoRequestDTO {

    @Positive(message = "El número de legajo debe ser positivo")
    private int nroLegajo;

    @NotBlank(message = "El modo no puede estar vacío")
    private String modo;

    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
}
