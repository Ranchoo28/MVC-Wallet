package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.RegexHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.model.EmailService;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.Period;

public class RegistrationController {
    private boolean isGoodUsername;
    private boolean isGoodEmail;
    private boolean isGoodPassword;
    private boolean isGoodAge;

    @FXML
    private DatePicker Birthday;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username, Email, Nome, Cognome;
    @FXML
    private Button buttonRegisterAccount = new Button();


    @FXML
    void onCancelButtonClick() { SceneHandler.getInstance().createLoginScene(); }

    @FXML
    void onRegisterAccountButtonClick() {
        // Una volta premuto il bottone, effettua la registrazion, manda una mail, effettua una query
        // per la registrazione e poi ti riporta alla schermata del login.

        if(SqlService.getIstance().serviceRegister(Email.getText(), Username.getText(), Password.getText(), Birthday.getValue(), Nome.getText(), Cognome.getText())){
            sendEmail();
            AlertHandler.getInstance().createRegistrationAlert();
        }

        else AlertHandler.getInstance().createErrorAlert("Username o email già presenti nel sistema");
    }

    @FXML
    void initialize() {

        buttonRegisterAccount.setDisable(true);

        Username.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se il nickname è formato da almeno 5 caratteri.
            isGoodUsername = newValue.length() > 5;

            performBinding();
        });

        Birthday.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Per vedere se un utente è maggiorenne, usiamo la classe Period che permette di calcolare il tempo
            // che passa fra una data e un'altra.
            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth());
            Period p = Period.between(birthday, today);

            isGoodAge = p.getYears() > 18;

            performBinding();
        });

        Email.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se la mail rispetta il Regex.
            isGoodEmail = newValue.matches(RegexHandler.getInstance().regexEmail);
            performBinding();
        });

        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se la password rispetta il Regex
            isGoodPassword = newValue.matches(RegexHandler.getInstance().regexPassword);

            performBinding();
        });
    }

    private void performBinding() {
        // Serve a disabilitare il button della registrazione qualora non venissero introdotte credenziali
        // valide durante la registrazione. Il runLater() serve ad assicuraci che questo codice venga eseguito
        // solamente dopo aver scritto nei vari textField.

        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(
                            Email.textProperty(),
                            Username.textProperty(),
                            Password.textProperty(),
                            Birthday.valueProperty()
                    );
                }

                @Override
                protected boolean computeValue() {
                    return !(isGoodEmail && isGoodAge && isGoodUsername && isGoodPassword);
                }
            };

            buttonRegisterAccount.disableProperty().bind(bb);
        });
    }

    public void sendEmail() {
        // Manda una mail dopo essersi registrati.
       EmailService.getInstance().startThreadEmail(Email.getText(),
               "MVC Wallet",
               "Ciao " + Nome.getText() + ", ti ringraziamo per aver effettuato la registrazione!");
    }
}
