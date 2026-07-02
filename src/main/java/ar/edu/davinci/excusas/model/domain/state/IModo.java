package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.Excusa;

// state interface — defines how a handler behaves in a given work mode
public interface IModo {
    void manejar(ar.edu.davinci.excusas.model.domain.chain.Encargado encargado, Excusa excusa);
}
