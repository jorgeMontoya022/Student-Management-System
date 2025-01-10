package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services.INotification;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EmailUtil implements INotification {
    private String recipient, subject, message;
    private static final Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, "teayudamo3@gmail.com", "xngu vxtd psds dbmt")
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withSessionTimeout(120000) // 2 minutos
            .withDebugLogging(true)
            .buildMailer();

    public EmailUtil(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public void sendNotification() {
        Email email = EmailBuilder.startingBlank()
                .from("teayudamo3@gmail.com")
                .to(recipient)
                .withSubject(subject)
                .withPlainText(message)
                .buildEmail();

        try {
            mailer.sendMail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}