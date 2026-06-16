package ar.edu.davinci.excusas.model.domain.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика для управления состояниями (Modos).
 * Реализует "Delivery de estados" для динамического разрешения зависимостей.
 */
public class StateFactory {
    private static Map<String, IModo> registry = new HashMap<>();

    static {
        // Регистрируем базовые состояния
        registry.put("PRODUCTIVO", new ModoProductivo());
        registry.put("NORMAL", new ModoNormal());
        registry.put("VAGO", new ModoVago());
    }

    public static IModo getModo(String tipo) {
        return registry.getOrDefault(tipo.toUpperCase(), registry.get("NORMAL"));
    }

    /**
     * Позволяет динамически добавлять новые состояния без изменения кода фабрики.
     */
    public static void registrarModo(String nombre, IModo modo) {
        registry.put(nombre.toUpperCase(), modo);
    }
}
