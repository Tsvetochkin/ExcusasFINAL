package ar.edu.davinci.excusas.model.domain;

public interface IExcusa {
    boolean esTrivial();
    boolean esModerada();
    boolean esCompleja();
    boolean esInverosimil();
    void ejecutarAccion(EmailSender emailSender);
}
