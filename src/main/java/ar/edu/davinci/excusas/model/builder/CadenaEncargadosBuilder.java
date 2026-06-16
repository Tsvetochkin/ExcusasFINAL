package ar.edu.davinci.excusas.model.builder;

import ar.edu.davinci.excusas.model.controller.CadenaDeEncargados;
import ar.edu.davinci.excusas.model.controller.ExcusaController;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.chain.impl.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Строитель для сборки цепочки ответственных.
 * Паттерн Builder.
 */
public class CadenaEncargadosBuilder {
    private List<Encargado> encargados = new ArrayList<>();

    public CadenaEncargadosBuilder conRecepcionista(String nombre, String email, int legajo) {
        this.encargados.add(new Recepcionista(nombre, email, legajo));
        return this;
    }

    public CadenaEncargadosBuilder conSupervisora(String nombre, String email, int legajo) {
        this.encargados.add(new Supervisora(nombre, email, legajo));
        return this;
    }

    public CadenaEncargadosBuilder conCEO(String nombre, String email, int legajo) {
        this.encargados.add(new CEO(nombre, email, legajo));
        return this;
    }

    public CadenaEncargadosBuilder conEncargadoPorDefecto(String nombre, String email, int legajo) {
        this.encargados.add(new EncargadoDefecto(nombre, email, legajo));
        return this;
    }

    /**
     * Основной метод: связывает всех добавленных людей в цепь и 
     * возвращает готовый контроллер.
     */
    public ExcusaController build() {
        if (encargados.isEmpty()) {
            throw new IllegalStateException("No se puede construir una cadena vacía!");
        }

        // Связываем элементы между собой
        for (int i = 0; i < encargados.size() - 1; i++) {
            encargados.get(i).setSiguiente(encargados.get(i + 1));
        }

        // Возвращаем контроллер, настроенный на первый элемент
        return new CadenaDeEncargados(encargados.get(0));
    }
}
