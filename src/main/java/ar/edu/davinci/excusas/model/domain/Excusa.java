package ar.edu.davinci.excusas.model.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "excusas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_excusa")
public abstract class Excusa implements IExcusa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    private boolean aceptada;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoExcusa motivo;

    // Nombre del encargado que aceptó/rechazó la excusa (para filtros y prontuarios)
    private String aceptadaPor;

    protected Excusa() {}

    public Excusa(Empleado empleado, TipoExcusa motivo) {
        this.empleado = empleado;
        this.aceptada = false;
        this.fecha = LocalDate.now();
        this.motivo = motivo;
    }

    public Long getId() { return id; }
    public Empleado getEmpleado() { return empleado; }
    public boolean isAceptada() { return aceptada; }
    public void setAceptada(boolean aceptada) { this.aceptada = aceptada; }
    public LocalDate getFecha() { return fecha; }
    public TipoExcusa getMotivo() { return motivo; }
    public String getAceptadaPor() { return aceptadaPor; }
    public void setAceptadaPor(String nombre) { this.aceptadaPor = nombre; }

    // По умолчанию все проверки возвращают false. 
    @Override
    public boolean esTrivial() { return false; }

    @Override
    public boolean esModerada() { return false; }

    @Override
    public boolean esCompleja() { return false; }

    @Override
    public boolean esInverosimil() { return false; }

    @Override
    public abstract void ejecutarAccion(EmailSender emailSender);
}
