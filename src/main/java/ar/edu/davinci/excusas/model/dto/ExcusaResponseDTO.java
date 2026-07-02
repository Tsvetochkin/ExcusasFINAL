package ar.edu.davinci.excusas.model.dto;

import java.time.LocalDate;

public class ExcusaResponseDTO {
    private Long id;
    private int legajoEmpleado;
    private String nombreEmpleado;
    private String motivo;
    private boolean aceptada;
    private LocalDate fecha;
    private String aceptadaPor; // name of the handler who processed it

    public ExcusaResponseDTO(Long id, int legajoEmpleado, String nombreEmpleado,
                              String motivo, boolean aceptada, LocalDate fecha, String aceptadaPor) {
        this.id = id;
        this.legajoEmpleado = legajoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.motivo = motivo;
        this.aceptada = aceptada;
        this.fecha = fecha;
        this.aceptadaPor = aceptadaPor;
    }

    public Long getId() { return id; }
    public int getLegajoEmpleado() { return legajoEmpleado; }
    public String getNombreEmpleado() { return nombreEmpleado; }
    public String getMotivo() { return motivo; }
    public boolean isAceptada() { return aceptada; }
    public LocalDate getFecha() { return fecha; }
    public String getAceptadaPor() { return aceptadaPor; }
}
