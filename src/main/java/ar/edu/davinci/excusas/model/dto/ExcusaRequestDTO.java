package ar.edu.davinci.excusas.model.dto;

// Lo que el cliente envía para registrar una excusa:
// { "legajo": 501, "motivo": "TRIVIAL" }
public class ExcusaRequestDTO {
    private int legajo;
    private String motivo; // String porque el cliente manda texto, lo convertimos a enum en el Service

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
