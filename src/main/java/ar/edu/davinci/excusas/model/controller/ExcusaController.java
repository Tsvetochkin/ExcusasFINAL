package ar.edu.davinci.excusas.model.controller;

import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Интерфейс контроллера, который управляет процессом обработки оправданий.
 */
public interface ExcusaController {
    void manejar(Excusa excusa);
}
