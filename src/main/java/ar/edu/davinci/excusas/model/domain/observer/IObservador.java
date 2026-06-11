package ar.edu.davinci.excusas.model.domain.observer;

import ar.edu.davinci.excusas.model.domain.Prontuario;

public interface IObservador {
    void actualizar(Prontuario prontuario);
}
