package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.EmailHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {
    private static final EmailHandler emailHandler = EmailHandler.getInstance();
    private EmailService(){}
    private static final EmailService instance = new EmailService();
    public static EmailService getInstance(){return instance;}

    // Service per l'invio di una mail. Senza questo l'app si bloccherebbe finchè l'invio della mail non viene completato.
    public void emailServiceSendWelcomeEmail(String toEmail, String subject, String body){

        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(() -> emailHandler.sendEmail(toEmail, subject, body));
        emailExecutor.shutdown();
    }

    public void emailServiceForgotPassword(String toEmail, String subject, String body, String token){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(() -> emailHandler.sendForgotPasswordEmail(toEmail, subject, body, token));
        emailExecutor.shutdown();
    }
}
