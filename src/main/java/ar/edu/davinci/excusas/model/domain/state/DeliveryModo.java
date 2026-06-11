package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModo {
    private List<IModo> modos;

    public DeliveryModo() {
        this.modos = new ArrayList<>();
        this.modos.add(new ModoProductivo());
        this.modos.add(new ModoNormal());
        this.modos.add(new ModoVago());
    }

    public void agregar(IModo modo) {
        this.modos.add(modo);
    }

    public void sacar(IModo modo) {
        this.modos.remove(modo);
    }

    private IModo modoPara(Encargado unEncargado) {
        int procesadas = unEncargado.getExcusasProcesadas();
        // Уточненная логика по ТЗ:
        // "menos de 5" (0,1,2,3,4) -> Productivo
        // "más de 5 pero menos de 10" (тут обычно имеют в виду 5-10 включительно) -> Normal
        // "11 o más" -> Vago
        if (procesadas < 5) {
            return buscarModo(ModoProductivo.class);
        } else if (procesadas <= 10) {
            return buscarModo(ModoNormal.class);
        } else {
            return buscarModo(ModoVago.class);
        }
    }

    private IModo buscarModo(Class<? extends IModo> clase) {
        return modos.stream()
                .filter(clase::isInstance)
                .findFirst()
                .orElse(modos.get(1));
    }

    public void actuar(Encargado unEncargado, Excusa unaExcusa) {
        this.modoPara(unEncargado).manejar(unEncargado, unaExcusa);
    }

    public void tomarCafe(Encargado e) {
        System.out.println("Tomando café...");
        e.resetExcusasProcesadas(); 
    }

    public void tomarBreak(Encargado e, int minutos) {
        if (minutos >= 10 && minutos <= 15) {
            System.out.println("Break correcto.");
            e.setExcusasProcesadas(5); // Переводим в Normal (5-10)
        }
    }
}
