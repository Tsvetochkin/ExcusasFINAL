package ar.edu.davinci.excusas.model.builder;

import ar.edu.davinci.excusas.model.controller.CadenaDeEncargados;
import ar.edu.davinci.excusas.model.controller.ExcusaController;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import java.util.ArrayList;
import java.util.List;

/**
 * Строитель для сборки цепочки ответственных.
 * Паттерн Builder.
 */
public class CadenaEncargadosBuilder {
    private List<Encargado> encargados = new ArrayList<>();

    /**
     * Добавляет нового ответственного в будущую цепочку.
     * Возвращает 'this', чтобы можно было делать цепочку вызовов (Fluent API).
     */
    public CadenaEncargadosBuilder agregar(Encargado encargado) {
        this.encargados.add(encargado);
        return this;
    }

    /**
     * Основной метод: связывает всех добавленных людей в цепь и 
     * возвращает готовый контроллер.
     */
    public ExcusaController build() {
        if (encargados.isEmpty()) {
            throw new IllegalStateException("No se может построить пустую цепочку!");
        }

        // Связываем элементы между собой
        for (int i = 0; i < encargados.size() - 1; i++) {
            encargados.get(i).setSiguiente(encargados.get(i + 1));
        }

        // Возвращаем контроллер, настроенный на первый элемент
        return new CadenaDeEncargados(encargados.get(0));
    }
}
