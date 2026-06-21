package ar.edu.davinci.excusas.model.dto;

// Para PUT /encargados/modo: cambiar el modo de trabajo
// { "nroLegajo": 101, "modo": "VAGO" }
// El modo debe ser uno de: NORMAL, VAGO, PRODUCTIVO (valores del enum ModoTrabajo)
public class ModoRequestDTO {
    private int nroLegajo;
    private String modo;

    public int getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(int nroLegajo) { this.nroLegajo = nroLegajo; }
    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
}
