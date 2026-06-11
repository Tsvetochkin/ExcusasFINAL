package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;

public interface IObservable {
    void agregarObservador(IObservador observador);
    void notificarObservadores(Prontuario prontuario);
}
