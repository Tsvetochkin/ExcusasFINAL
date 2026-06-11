package ar.edu.davinci.excusas.model.domain.chain;

import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Контракт для любого лица, принимающего решения по оправданиям.
 */
public interface IEncargado {
    void revisarExcusa(Excusa excusa);
}
