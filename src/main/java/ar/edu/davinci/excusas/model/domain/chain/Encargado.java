package ar.edu.davinci.excusas.model.domain.chain;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.state.DeliveryModo;

public abstract class Encargado extends Empleado implements IEncargado {
    protected Encargado siguiente;
    private DeliveryModo deliveryModo;
    private int excusasProcesadas;

    public Encargado(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
        this.deliveryModo = new DeliveryModo();
        this.excusasProcesadas = 0;
    }

    public void manejar(Excusa excusa) {
        this.deliveryModo.actuar(this, excusa);
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        // Template Method (стиль преподавателя)
        if (this.puedeManejar(excusa)) {
            this.procesar(excusa);
            this.excusasProcesadas++;
        } else {
            this.derivar(excusa);
        }
    }

    public abstract boolean puedeManejar(Excusa excusa);

    protected abstract void procesar(Excusa excusa);

    public void derivar(Excusa excusa) {
        if (this.siguiente != null) {
            System.out.println(this.getClass().getSimpleName() + " derivando...");
            this.siguiente.manejar(excusa);
        } else {
            System.out.println("Excusa rechazada: necesitamos pruebas contundentes");
        }
    }

    public void setSiguiente(Encargado siguiente) {
        this.siguiente = siguiente;
    }

    public int getExcusasProcesadas() {
        return excusasProcesadas;
    }

    public void setExcusasProcesadas(int val) {
        this.excusasProcesadas = val;
    }

    public void resetExcusasProcesadas() {
        this.excusasProcesadas = 0;
    }

    public DeliveryModo getDeliveryModo() {
        return deliveryModo;
    }
}
