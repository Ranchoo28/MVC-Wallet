package it.unical.demacs.informatica.mvcwallet.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailHandler {
    private EmailHandler(){}
    private static final EmailHandler instance = new EmailHandler();
    public static EmailHandler getInstance(){return instance;}

    public void sendEmail(String toEmail, String subject, String body) {
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

}
