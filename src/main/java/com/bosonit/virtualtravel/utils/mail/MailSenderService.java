package com.bosonit.virtualtravel.utils.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;


@Slf4j
@Service
public class MailSenderService {
    // Introducimos estos valores como parametro al inicio
    // para una mayor seguridad
    @Value("${virtual.email}")
    private String fromEmail;

    @Value("${virtual.email.pass}")
    private String password;

    public void sendMail(String to, String subject, String body) {

        log.info("Comienza servicio de envio de mensaje");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session, to, subject, body);
    }
}