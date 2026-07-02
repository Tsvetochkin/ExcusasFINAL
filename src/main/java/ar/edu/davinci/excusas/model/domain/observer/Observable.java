package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<IObservador> observadores = new ArrayList<>();

    public void agregarObservador(IObservador observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void quitarObservador(IObservador observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(Prontuario prontuario) {
        for (IObservador observador : observadores) {
            observador.actualizar(prontuario);
        }
    }
}
