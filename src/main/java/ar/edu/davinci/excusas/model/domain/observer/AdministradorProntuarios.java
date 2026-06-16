package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Синглтон для управления пронтуарио (списком прогулов).
 * Наследуется от Observable для уведомления других отделов.
 */
public class AdministradorProntuarios extends Observable {
    private static AdministradorProntuarios instancia;
    private List<Prontuario> prontuarios;

    private AdministradorProntuarios() {
        this.prontuarios = new ArrayList<>();
    }

    public static synchronized AdministradorProntuarios getInstancia() {
        if (instancia == null) {
            instancia = new AdministradorProntuarios();
        }
        return instancia;
    }

    public void registrarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
        notificarObservadores(prontuario);
    }

    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(prontuarios);
    }
}
