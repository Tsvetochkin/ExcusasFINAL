package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.Excusa;

public class ModoProductivo implements IModo {
    @Override
    public void manejar(Encargado encargado, Excusa excusa) {
        System.out.println(encargado.getNombre() + " (Productivo)");
        if (encargado.puedeManejar(excusa)) {
            encargado.revisarExcusa(excusa);
        } else {
            System.out.println("Email al CTO: " + encargado.getNombre() + " no pudo procesar.");
            encargado.derivar(excusa);
        }
    }
}
