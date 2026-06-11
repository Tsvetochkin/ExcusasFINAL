package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;
import java.util.ArrayList;
import java.util.List;

public class AdministradorProntuarios implements IObservable {
    private static AdministradorProntuarios instancia;
    private List<Prontuario> prontuarios;
    private List<IObservador> observadores;

    private AdministradorProntuarios() {
        this.prontuarios = new ArrayList<>();
        this.observadores = new ArrayList<>();
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

    @Override
    public void agregarObservador(IObservador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void notificarObservadores(Prontuario prontuario) {
        for (IObservador observador : observadores) {
            observador.actualizar(prontuario);
        }
    }

    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(prontuarios);
    }
}
