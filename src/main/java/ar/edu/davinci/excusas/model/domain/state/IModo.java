package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Интерфейс состояния обработчика.
 * Определяет, как именно будет обрабатываться оправдание в данном состоянии.
 */
public interface IModo {
    // Временно используем Object для Encargado, пока не создадим сам класс Encargado,
    // чтобы не было ошибок компиляции, или создадим пустой класс Encargado.
    void manejar(ar.edu.davinci.excusas.model.domain.chain.Encargado encargado, Excusa excusa);
}
