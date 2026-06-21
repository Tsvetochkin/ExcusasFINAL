package ar.edu.davinci.excusas.model.service;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.ModoTrabajo;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.chain.impl.*;
import ar.edu.davinci.excusas.model.domain.state.StateFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Este servicio administra la cadena de encargados como un Singleton de Spring.
// Al ser @Service, Spring crea una sola instancia para toda la app.
@Service
public class CadenaService {

    private final List<Encargado> encargados = new ArrayList<>();
    private Encargado primero; // cabeza de la cadena

    public CadenaService() {
        // Construimos la cadena inicial al arrancar
        Encargado recepcionista = new Recepcionista("Ana", "ana@empresa.com", 101);
        Encargado supervisora   = new Supervisora("Berta", "berta@empresa.com", 102);
        Encargado ceo           = new CEO("Elon Musk", "elon@empresa.com", 1);
        Encargado defecto       = new EncargadoDefecto("Bot de Rechazo", "bot@empresa.com", 999);

        encargados.add(recepcionista);
        encargados.add(supervisora);
        encargados.add(ceo);
        encargados.add(defecto);

        reconstruirCadena();
    }

    // Evalúa una excusa con la cadena actual
    public void evaluar(Excusa excusa) {
        primero.manejar(excusa);
    }

    // Agrega un nuevo encargado dinámico ANTES del encargado por defecto
    public void agregarEncargado(String nombre, String email, int nroLegajo, List<TipoExcusa> motivos) {
        EncargadoDinamico nuevo = new EncargadoDinamico(nombre, email, nroLegajo, motivos);
        // Lo insertamos antes del último (EncargadoDefecto)
        encargados.add(encargados.size() - 1, nuevo);
        reconstruirCadena();
    }

    // Cambia el modo de un encargado buscándolo por legajo
    // Valida con el enum ModoTrabajo que el valor sea válido
    public boolean cambiarModo(int nroLegajo, String modo) {
        try {
            ModoTrabajo.valueOf(modo.toUpperCase()); // valida que exista en el enum
        } catch (IllegalArgumentException e) {
            return false;
        }
        for (Encargado e : encargados) {
            if (e.getNroLegajo() == nroLegajo) {
                e.setModoForzado(modo.toUpperCase());
                return true;
            }
        }
        return false;
    }

    // Devuelve la configuración actual para GET /encargados
    public List<EncargadoInfoDTO> getInfo() {
        List<EncargadoInfoDTO> info = new ArrayList<>();
        for (Encargado e : encargados) {
            String modo = e.getModoForzado() != null ? e.getModoForzado() : "AUTOMATICO";
            info.add(new EncargadoInfoDTO(e.getNombre(), e.getNroLegajo(),
                    e.getClass().getSimpleName(), modo));
        }
        return info;
    }

    // Reconstruye los enlaces entre encargados cada vez que cambia la lista
    private void reconstruirCadena() {
        for (int i = 0; i < encargados.size() - 1; i++) {
            encargados.get(i).setSiguiente(encargados.get(i + 1));
        }
        encargados.get(encargados.size() - 1).setSiguiente(null);
        primero = encargados.get(0);
    }

    // DTO interno para la respuesta de GET /encargados
    public static class EncargadoInfoDTO {
        public final String nombre;
        public final int nroLegajo;
        public final String tipo;
        public final String modo;

        public EncargadoInfoDTO(String nombre, int nroLegajo, String tipo, String modo) {
            this.nombre = nombre;
            this.nroLegajo = nroLegajo;
            this.tipo = tipo;
            this.modo = modo;
        }
    }
}
