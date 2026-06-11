package ar.edu.davinci.excusas.model.domain;

public interface EmailSender {
    void enviarEmail(String dest, String orig, String asunto, String cuerpo);
}
