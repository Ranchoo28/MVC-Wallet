package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.EmailService;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.time.LocalDate;
import java.time.Period;
import java.util.EventListener;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameText, emailText, nameText, surnameText;
    @FXML
    private Button buttonRegisterAccount, backButton;
    @FXML
    private Label usernameLabel, birthdayLabel, nameLabel, surnameLabel;
    @FXML
    private MenuButton ddMenuButton, mmMenuButton, yyMenuButton;

    private boolean isGoodUsername;
    private boolean isGoodEmail;
    private boolean isGoodPassword;
    private boolean isGoodAge;

    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final EmailService emailService = EmailService.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();
    private final RegexHandler regexHandler = RegexHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();

    LocalDate today = LocalDate.now();
    @FXML
   void onCancelButtonClick() { sceneHandler.createLoginScene(); }

    @FXML
    void onRegisterAccountButtonClick() {
       // Una volta premuto il bottone, effettua la registrazion, manda una mail, effettua una query
       // per la registrazione e poi ti riporta alla schermata del login.
       int year = Integer.parseInt(yyMenuButton.getText());
       int month = Integer.parseInt(mmMenuButton.getText());
       int day = Integer.parseInt(ddMenuButton.getText());

       LocalDate date = LocalDate.of(year, month, day);

        if(sqlService.serviceRegister(emailText.getText(), usernameText.getText(), passwordField.getText(), date, nameText.getText(), surnameText.getText())){
            sendEmail();
            alertHandler.createRegistrationAlert();
        }

        else alertHandler.createErrorAlert(lanHandler.getBundle().getString("registrationErrorText"));
    }

    public boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                // Anno divisibile per 100 ma non per 400, quindi non bisestile
                return year % 400 == 0; // Anno divisibile per 400, quindi bisestile
            } else {
                return true; // Anno divisibile per QUATTRO ma non per 100, quindi bisestile
            }
        } else {
            return false; // Anno non divisibile per QUATTRO, quindi non bisestile
        }
    }

    int getDaysInMonth(String month) {
        if (Objects.equals(month, "04") || Objects.equals(month, "06") || Objects.equals(month, "09") || Objects.equals(month, "11")) {
            return 30;
        } else {
            return 31;
        }
    }

    void addDay() {
        String month = mmMenuButton.getText();
        String year = yyMenuButton.getText();

        ddMenuButton.getItems().clear();

        if (Objects.equals(month, "MM")) {
            // Selezionato valore "MM" nel menu del mese, aggiungi tutti i giorni
            for (int i = 1; i <= 31; i++) {
                String day = String.format("%02d", i);
                MenuItem item = new MenuItem(day);
                item.setOnAction(event -> {
                    ddMenuButton.setText(item.getText());
                });
                ddMenuButton.getItems().add(item);
            }
        } else if (Objects.equals(month, "02")) {
            // Mese di febbraio
            if (isLeapYear(Integer.parseInt(year))) {
                // Anno bisestile, aggiungi 29 giorni
                for (int i = 1; i <= 29; i++) {
                    String day = String.format("%02d", i);
                    MenuItem item = new MenuItem(day);
                    item.setOnAction(event -> {
                        ddMenuButton.setText(item.getText());
                    });
                    ddMenuButton.getItems().add(item);
                }
            } else {
                // Anno non bisestile, aggiungi 28 giorni
                for (int i = 1; i <= 28; i++) {
                    String day = String.format("%02d", i);
                    MenuItem item = new MenuItem(day);
                    item.setOnAction(event -> {
                        ddMenuButton.setText(item.getText());
                    });
                    ddMenuButton.getItems().add(item);
                }
            }
        } else {
            // Altri mesi con 30 o 31 giorni
            int daysInMonth = getDaysInMonth(month);
            for (int i = 1; i <= daysInMonth; i++) {
                String day = String.format("%02d", i);
                MenuItem item = new MenuItem(day);
                item.setOnAction(event -> {
                    ddMenuButton.setText(item.getText());
                });
                ddMenuButton.getItems().add(item);
            }
        }
    }

    private void addMonth(){
        for(int i=1; i<=12 ; i++){
            String month = String.format("%02d", i);
            MenuItem item = new MenuItem(String.valueOf(month));
            item.setOnAction(event -> {
                mmMenuButton.setText(item.getText());
            });
            mmMenuButton.getItems().add(item);
        }
    }
    private void addYear(){
        for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
            MenuItem item = new MenuItem(String.valueOf(i));
            item.setOnAction(event -> {
                yyMenuButton.setText(item.getText());
            });
            yyMenuButton.getItems().add(item);
        }
    }

    private boolean checkBirthDate(){
        try {
            int year = Integer.parseInt(yyMenuButton.getText());
            int month = Integer.parseInt(mmMenuButton.getText());
            int day = Integer.parseInt(ddMenuButton.getText());
            LocalDate birthday = LocalDate.of(year, month, day);
            Period p = Period.between(birthday, today);

            return p.getYears() > 18;

        } catch (Exception ignored){
            return false;
        }
    }

   @FXML
   void initialize() {
        addDay();
        addMonth();
        addYear();
        updateLanguage();
        addListener();
        setToolTip();
    }

    public void sendEmail() {
        // Manda una mail dopo essersi registrati.
       emailService.emailServiceSendWelcomeEmail(emailText.getText(), "MVC Wallet",
       "Ciao " + usernameText.getText() + ", ti ringraziamo per aver effettuato la registrazione!");
    }

    private void addListener(){
        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addDay();
            }
        };

        ChangeListener<String> dateOfBirth = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                isGoodAge = checkBirthDate();
                performBinding();
            }
        };

        ddMenuButton.textProperty().addListener(dateOfBirth);
        ddMenuButton.textProperty().addListener(dateOfBirth);
        ddMenuButton.textProperty().addListener(dateOfBirth);

        ddMenuButton.textProperty().addListener(listener);
        mmMenuButton.textProperty().addListener(listener);
        yyMenuButton.textProperty().addListener(listener);

       usernameText.textProperty().addListener((observable, oldValue, newValue) -> {
           // Controlla se il nickname è formato da almeno CINQUE caratteri.
           isGoodUsername = newValue.length() >= 5;

           performBinding();
       });

       emailText.textProperty().addListener((observable, oldValue, newValue) -> {
           // Controlla se la mail rispetta il Regex.
           isGoodEmail = newValue.matches(regexHandler.regexEmail);
           performBinding();
       });

       passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
           // Controlla se la password rispetta il Regex
           isGoodPassword = newValue.matches(regexHandler.regexPassword);

           performBinding();
       });
   }

   private void setToolTip(){
       usernameText.setTooltip(new Tooltip(bundle.getString("tooltipUsername")));
       passwordField.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
   }

   private void performBinding() {
       // Serve a disabilitare il button della registrazione qualora non venissero introdotte credenziali
       // valide durante la registrazione. Il runLater() serve ad assicuraci che questo codice venga eseguito
       // solamente dopo aver scritto nei vari textField.

       Platform.runLater(() -> {
           BooleanBinding bb = new BooleanBinding() {
               {
                   super.bind(
                       emailText.textProperty(),
                       usernameText.textProperty(),
                       passwordField.textProperty()
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

       private void updateLanguage() {
           ResourceBundle bundle = null;
           try {
               System.out.println(SettingsHandler.getInstance().loginLanguage);
               bundle = lanHandler.getBundle();
           } catch (Exception e) {
               alertHandler.createErrorAlert("Error in loading the language");
           }
           if (bundle != null) {
               buttonRegisterAccount.setText(bundle.getString("registerButton"));
               backButton.setText(bundle.getString("backButton"));
               nameLabel.setText(bundle.getString("nameLabel"));
               surnameLabel.setText(bundle.getString("surnameLabel"));
               birthdayLabel.setText(bundle.getString("birthdayLabel"));
           }
       }
}
