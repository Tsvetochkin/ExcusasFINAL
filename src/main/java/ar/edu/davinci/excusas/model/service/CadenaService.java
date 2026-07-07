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

// Spring singleton — one shared handler chain for the whole app
@Service
public class CadenaService {

    private final List<Encargado> encargados = new ArrayList<>();
    private Encargado primero; // head of the chain

    public CadenaService() {
        Encargado recepcionista = new Recepcionista("Gomez", "gomez@empresa.com", 101);
        Encargado supervisora   = new Supervisora("Bergamot", "bergamot@empresa.com", 102);
        Encargado ceo           = new CEO("Santa Clause", "santa@empresa.com", 1);
        Encargado defecto       = new EncargadoDefecto("Bot de Rechazo", "bot@empresa.com", 999);

        encargados.add(recepcionista);
        encargados.add(supervisora);
        encargados.add(ceo);
        encargados.add(defecto);

        reconstruirCadena();
    }

    public void evaluar(Excusa excusa) {
        primero.manejar(excusa);
    }

    // added just before the fallback handler so it still gets a chance to catch leftovers
    public void agregarEncargado(String nombre, String email, int nroLegajo, List<TipoExcusa> motivos) {
        EncargadoDinamico nuevo = new EncargadoDinamico(nombre, email, nroLegajo, motivos);
        encargados.add(encargados.size() - 1, nuevo); // insert before last (EncargadoDefecto)
        reconstruirCadena();
    }

    public boolean cambiarModo(int nroLegajo, String modo) {
        try {
            ModoTrabajo.valueOf(modo.toUpperCase()); // validates against the enum before searching
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

    public List<EncargadoInfoDTO> getInfo() {
        List<EncargadoInfoDTO> info = new ArrayList<>();
        for (Encargado e : encargados) {
            String modo = e.getModoForzado() != null ? e.getModoForzado() : modoEfectivo(e);
            info.add(new EncargadoInfoDTO(e.getNombre(), e.getNroLegajo(),
                    e.getClass().getSimpleName(), modo));
        }
        return info;
    }

    private String modoEfectivo(Encargado e) {
        int procesadas = e.getExcusasProcesadas();
        if (procesadas < 5) return "PRODUCTIVO";
        if (procesadas <= 10) return "NORMAL";
        return "VAGO";
    }

    // re-links the whole list after every add or remove
    private void reconstruirCadena() {
        for (int i = 0; i < encargados.size() - 1; i++) {
            encargados.get(i).setSiguiente(encargados.get(i + 1));
        }
        encargados.get(encargados.size() - 1).setSiguiente(null);
        primero = encargados.get(0);
    }

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
