package ar.edu.davinci.excusas.model.domain.chain;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.state.DeliveryModo;

// base class for all handlers in the chain — implements Template Method pattern
public abstract class Encargado extends Empleado implements IEncargado {
    protected Encargado siguiente;
    private DeliveryModo deliveryModo;
    private int excusasProcesadas;
    private String modoForzado; // null = automatic, "VAGO"/"NORMAL"/"PRODUCTIVO" = forced via API

    public Encargado(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
        this.deliveryModo = new DeliveryModo();
        this.excusasProcesadas = 0;
    }

    public void setModoForzado(String modo) { this.modoForzado = modo; }
    public String getModoForzado() { return modoForzado; }

    // delegates to the current state (DeliveryModo) which picks normal/vago/productivo
    public final void manejar(Excusa excusa) {
        this.deliveryModo.actuar(this, excusa);
    }

    // Template Method — final so subclasses can't reorder handle vs pass-on logic
    @Override
    public final void revisarExcusa(Excusa excusa) {
        if (this.puedeManejar(excusa)) {
            this.procesar(excusa);
            excusa.setAceptadaPor(this.getNombre());
            this.excusasProcesadas++;
        } else {
            this.derivar(excusa);
        }
    }

    // subclasses must implement these (primitive operations)
    public abstract boolean puedeManejar(Excusa excusa);

    protected abstract void procesar(Excusa excusa);

    public void derivar(Excusa excusa) {
        if (this.siguiente != null) {
            System.out.println(this.getClass().getSimpleName() + " (" + getNombre() + ") derivando excusa...");
            this.siguiente.revisarExcusa(excusa);
        } else {
            System.out.println("Fin de la cadena: La excusa no pudo ser procesada.");
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
}
