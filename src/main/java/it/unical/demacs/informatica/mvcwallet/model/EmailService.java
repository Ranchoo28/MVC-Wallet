package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.EmailHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {
    private EmailService(){}
    private static final EmailService instance = new EmailService();
    public static EmailService getInstance(){return instance;}

    // Service per l'invio di una mail. Senza questo l'app si bloccherebbe finchè l'invio della mail non viene completato.
    public void startThreadEmail(String toEmail, String subject, String body){

        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(() -> EmailHandler.getInstance().sendEmail(toEmail, subject, body));
        emailExecutor.shutdown();
    }

    public void startThreadForgotPassword(String toEmail, String subject, String body, String newPassword){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(() -> EmailHandler.getInstance().sendForgotPasswordEmail(toEmail, subject, body, newPassword));
        emailExecutor.shutdown();
    }
}
