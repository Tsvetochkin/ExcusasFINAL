package ar.edu.davinci.excusas.model.domain;

/**
 * Базовый абстрактный класс для всех оправданий.
 * Реализует общую логику: хранение сотрудника и статус принятия.
 */
public abstract class Excusa implements IExcusa {
    private Empleado empleado;
    private boolean aceptada;

    public Excusa(Empleado empleado) {
        this.empleado = empleado;
        this.aceptada = false; // По умолчанию оправдание не принято
    }

    // По умолчанию все проверки возвращают false. 
    // Наследники переопределят нужные.
    @Override
    public boolean esTrivial() { return false; }

    @Override
    public boolean esModerada() { return false; }

    @Override
    public boolean esCompleja() { return false; }

    @Override
    public boolean esInverosimil() { return false; }

    public Empleado getEmpleado() {
        return empleado;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    // Метод остается абстрактным, так как каждое оправдание 
    // выполняет свое действие (отправляет разный текст письма)
    @Override
    public abstract void ejecutarAccion(EmailSender emailSender);
}
