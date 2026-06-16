package ar.edu.davinci.excusas.model.domain.chain;

import ar.edu.davinci.excusas.model.domain.Empleado;
import ar.edu.davinci.excusas.model.domain.Excusa;
import ar.edu.davinci.excusas.model.domain.state.DeliveryModo;

/**
 * Базовый класс для всех ответственных в цепочке.
 * Реализует паттерн Template Method.
 */
public abstract class Encargado extends Empleado implements IEncargado {
    protected Encargado siguiente;
    private DeliveryModo deliveryModo;
    private int excusasProcesadas;

    public Encargado(String nombre, String email, int nroLegajo) {
        super(nombre, email, nroLegajo);
        this.deliveryModo = new DeliveryModo();
        this.excusasProcesadas = 0;
    }

    /**
     * Основной метод для внешнего вызова. 
     * Делегирует выполнение текущему состоянию (Modo).
     */
    public final void manejar(Excusa excusa) {
        this.deliveryModo.actuar(this, excusa);
    }

    /**
     * Template Method. Определяет скелет алгоритма обработки оправдания.
     * Сделан final, чтобы наследники не могли изменить порядок действий.
     */
    @Override
    public final void revisarExcusa(Excusa excusa) {
        if (this.puedeManejar(excusa)) {
            this.procesar(excusa);
            this.excusasProcesadas++;
        } else {
            this.derivar(excusa);
        }
    }

    // Примитивные операции (Primitive Operations) - должны реализовать наследники
    public abstract boolean puedeManejar(Excusa excusa);

    protected abstract void procesar(Excusa excusa);

    /**
     * Базовый шаг: передача следующему в цепочке.
     */
    public void derivar(Excusa excusa) {
        if (this.siguiente != null) {
            System.out.println(this.getClass().getSimpleName() + " (" + getNombre() + ") derivando excusa...");
            this.siguiente.revisarExcusa(excusa); // Передаем на проверку следующему
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
