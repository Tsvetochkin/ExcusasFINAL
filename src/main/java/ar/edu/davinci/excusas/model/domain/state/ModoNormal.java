package ar.edu.davinci.excusas.model.domain.state;

import ar.edu.davinci.excusas.model.domain.chain.Encargado;
import ar.edu.davinci.excusas.model.domain.Excusa;

public class ModoNormal implements IModo {
    @Override
    public void manejar(Encargado encargado, Excusa excusa) {
        System.out.println(encargado.getNombre() + " (Normal)");
        encargado.revisarExcusa(excusa);
    }
}
