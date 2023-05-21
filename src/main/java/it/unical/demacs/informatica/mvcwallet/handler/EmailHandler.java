package it.unical.demacs.informatica.mvcwallet.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailHandler {
    private EmailHandler(){}
    private static final EmailHandler istance = new EmailHandler();
    public static EmailHandler getInstance(){return istance;}

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        // Codice per l'invio di una mail
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = new Properties();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("projectuid28@gmail.com");
        mailSender.setPassword("mhsujioysswltzpj");
        mailSender.setPort(587);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projectuid28@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }

    public void sendForgotPasswordEmail(String toEmail, String subject, String body, String newPass){
        // Codice per l'invio di una mail
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = new Properties();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("projectuid28@gmail.com");
        mailSender.setPassword("mhsujioysswltzpj");
        mailSender.setPort(587);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projectuid28@gmail.com");
        message.setTo(toEmail);
        message.setText(body + newPass);
        message.setSubject(subject);
        mailSender.send(message);
    }

    public void startThreadEmail(String toEmail, String subject, String body){
        // Thread per l'invio di una mail. Senza questo l'app si bloccherebbe finchè l'invio della mail non viene completato.
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sendSimpleEmail(toEmail, subject, body);
            }
        });
    }

    public void startThreadForgotPassword(String toEmail, String subject, String body, String newPassword){
        // Thread per l'invio di una mail. Senza questo l'app si bloccherebbe finchè l'invio della mail non viene completato.
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sendForgotPasswordEmail(toEmail, subject, body, newPassword);
            }
        });
    }
}
