package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.Excusa;

/**
 * Класс для доставки (delivery) состояний.
 * Выбирает нужный режим работы сотрудника в зависимости от его продуктивности.
 */
public class DeliveryModo {

    public void actuar(Encargado unEncargado, Excusa unaExcusa) {
        this.getModoPara(unEncargado).manejar(unEncargado, unaExcusa);
    }

    private IModo getModoPara(Encargado unEncargado) {
        // Si tiene modo forzado (puesto via API), lo usa
        if (unEncargado.getModoForzado() != null) {
            return StateFactory.getModo(unEncargado.getModoForzado());
        }
        // Si no, elige automáticamente por cantidad procesada
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
