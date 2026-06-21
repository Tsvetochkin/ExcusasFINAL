package ar.edu.davinci.excusas.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nroLegajo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excusa_id")
    private Excusa excusa;

    protected Prontuario() {}

    public Prontuario(Empleado empleado, Excusa excusa) {
        this.empleado = empleado;
        this.excusa = excusa;
        this.nroLegajo = empleado.getNroLegajo();
    }

    public Long getId() { return id; }
    public int getNroLegajo() { return nroLegajo; }
    public Empleado getEmpleado() { return empleado; }
    public Excusa getExcusa() { return excusa; }
}
