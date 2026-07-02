package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.Excusa;

// picks the right work mode for a handler based on how many excuses they've processed
public class DeliveryModo {

    public void actuar(Encargado unEncargado, Excusa unaExcusa) {
        this.getModoPara(unEncargado).manejar(unEncargado, unaExcusa);
    }

    private IModo getModoPara(Encargado unEncargado) {
        // if forced via API, use that
        if (unEncargado.getModoForzado() != null) {
            return StateFactory.getModo(unEncargado.getModoForzado());
        }
        // otherwise auto-pick based on how many they've already processed
        int procesadas = unEncargado.getExcusasProcesadas();
        if (procesadas < 5) {
            return StateFactory.getModo("PRODUCTIVO");
        } else if (procesadas <= 10) {
            return StateFactory.getModo("NORMAL");
        } else {
            return StateFactory.getModo("VAGO");
        }
    }

    public void tomarCafe(Encargado e) {
        System.out.println(e.getNombre() + " está tomando café... Reset de productividad.");
        e.resetExcusasProcesadas(); 
    }

    public void tomarBreak(Encargado e, int minutos) {
        if (minutos >= 10 && minutos <= 15) {
            System.out.println("Break de " + minutos + " min. Productividad restaurada a Normal.");
            e.setExcusasProcesadas(5); 
        }
    }
}
