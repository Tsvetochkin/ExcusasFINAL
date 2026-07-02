package ar.edu.davinci.excusas.model.dto;

// body: { "legajo": 501, "motivo": "TRIVIAL" }
public class ExcusaRequestDTO {
    private int legajo;
    private String motivo; // String — converted to enum in the service

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
