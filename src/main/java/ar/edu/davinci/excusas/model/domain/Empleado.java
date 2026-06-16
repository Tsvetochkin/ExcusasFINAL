package ar.edu.davinci.excusas.model.domain;

import jakarta.persistence.*;

/**
 * Класс Сотрудник, теперь это JPA Entity.
 * @Entity говорит Spring Boot, что этот класс соответствует таблице в базе данных.
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный ID для базы данных

    private String nombre;
    private String email;
    
    @Column(name = "nro_legajo")
    private int nroLegajo;

    // Hibernate требует наличия пустого конструктора (protected или public)
    protected Empleado() {}

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    // Геттеры и сеттеры (Hibernate использует их для доступа к данным)
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }
}
