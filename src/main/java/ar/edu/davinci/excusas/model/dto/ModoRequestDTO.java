package ar.edu.davinci.excusas.model.dto;

// body: { "nroLegajo": 101, "modo": "VAGO" } — valid values: NORMAL, VAGO, PRODUCTIVO
public class ModoRequestDTO {
    private int nroLegajo;
    private String modo;

    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
}
