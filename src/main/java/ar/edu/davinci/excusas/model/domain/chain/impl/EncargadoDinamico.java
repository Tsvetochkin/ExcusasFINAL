package ar.edu.davinci.excusas.model.domain.chain.impl;

import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.TipoExcusa;
import ar.edu.davinci.excusas.model.domain.chain.Encargado;

import java.util.List;

// Un encargado genérico que puede manejar cualquier lista de tipos de excusa
// Esto permite agregar encargados dinámicamente vía API
public class EncargadoDinamico extends Encargado {

    private final List<TipoExcusa> motivosQueManeja;

    public EncargadoDinamico(String nombre, String email, int nroLegajo, List<TipoExcusa> motivos) {
        super(nombre, email, nroLegajo);
        this.motivosQueManeja = motivos;
    }

    @Override
    public boolean puedeManejar(Excusa excusa) {
        return motivosQueManeja.contains(excusa.getMotivo());
    }

    @Override
    protected void procesar(Excusa excusa) {
        excusa.setAceptada(true);
        System.out.println(getNombre() + " procesando excusa dinámica tipo " + excusa.getMotivo());
    }

    public List<TipoExcusa> getMotivosQueManeja() { return motivosQueManeja; }
}
