package ar.edu.davinci.excusas.model.domain.chain;

import ar.edu.davinci.excusas.model.domain.Excusa;

// anyone who can review and decide on excuses
public interface IEncargado {
    void revisarExcusa(Excusa excusa);
}
