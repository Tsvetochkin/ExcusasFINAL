package ar.edu.davinci.excusas.model.domain.state;

import java.util.HashMap;
import java.util.Map;

// factory that caches all IModo instances — add new states without changing this class
public class StateFactory {
    private static Map<String, IModo> registry = new HashMap<>();

    static {
        registry.put("PRODUCTIVO", new ModoProductivo());
        registry.put("NORMAL", new ModoNormal());
        registry.put("VAGO", new ModoVago());
    }

    public static IModo getModo(String tipo) {
        return registry.getOrDefault(tipo.toUpperCase(), registry.get("NORMAL"));
    }

    // plug in new states at runtime without touching this class
    public static void registrarModo(String nombre, IModo modo) {
        registry.put(nombre.toUpperCase(), modo);
    }
}
