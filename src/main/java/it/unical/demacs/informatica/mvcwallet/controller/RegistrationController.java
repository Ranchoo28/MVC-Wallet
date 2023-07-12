package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.EmailService;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class RegistrationController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameText, emailText, nameText, surnameText;
    @FXML
    private Button buttonRegisterAccount, backButton, ddButton, mmButton, yyyyButton;
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
    @FXML
    void onCancelButtonClick(){}
    @FXML
    void onRegisterAccountButtonClick(){
        MenuItem item = new MenuItem("Ciao");
        ddContent.getItems().add(item);
    }
    @FXML
    ContextMenu ddContent;
    @FXML
    void ddClick(){
    }

//   @FXML
//   void onCancelButtonClick() { sceneHandler.createLoginScene(); }

//   @FXML
//   void onRegisterAccountButtonClick() {
//       // Una volta premuto il bottone, effettua la registrazion, manda una mail, effettua una query
//       // per la registrazione e poi ti riporta alla schermata del login.

//        if(sqlService.serviceRegister(emailText.getText(), usernameText.getText(), passwordField.getText(), birthdayPicker.getValue(), nameText.getText(), surnameText.getText())){
//            sendEmail();
//            alertHandler.createRegistrationAlert();
//        }
//
//        else alertHandler.createErrorAlert(lanHandler.getBundle().getString("registrationErrorText"));
//    }

 //   @FXML
 //   void initialize() {
//        buttonRegisterAccount.setDisable(true);
//        updateLanguage();
//        addListener();
//        setToolTip();
//    }

//    public void sendEmail() {
//        // Manda una mail dopo essersi registrati.
//       emailService.emailServiceSendWelcomeEmail(emailText.getText(),
//               "MVC Wallet",
//               "Ciao " + usernameText.getText() + ", ti ringraziamo per aver effettuato la registrazione!");
//    }

//   private void addListener(){
//       usernameText.textProperty().addListener((observable, oldValue, newValue) -> {
//           // Controlla se il nickname è formato da almeno 5 caratteri.
//           isGoodUsername = newValue.length() >= 5;

//           performBinding();
//       });

//           birthdayPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
//               // Per vedere se un utente è maggiorenne, usiamo la classe Period che permette di calcolare il tempo
//               // che passa fra una data e un'altra.
//               LocalDate today = LocalDate.now();
//               LocalDate birthday = LocalDate.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth());
//               Period p = Period.between(birthday, today);

//               isGoodAge = p.getYears() > 18;

//               performBinding();
//           });

//           emailText.textProperty().addListener((observable, oldValue, newValue) -> {
//               // Controlla se la mail rispetta il Regex.
//               isGoodEmail = newValue.matches(regexHandler.regexEmail);
//               performBinding();
//           });

//           passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
//               // Controlla se la password rispetta il Regex
//               isGoodPassword = newValue.matches(regexHandler.regexPassword);

//               performBinding();
//           });
//       }

//       private void setToolTip(){
//           usernameText.setTooltip(new Tooltip(bundle.getString("tooltipUsername")));
//           passwordField.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
//           birthdayPicker.setTooltip(new Tooltip(bundle.getString("tooltipAge")));
//       }

//       private void performBinding() {
//           // Serve a disabilitare il button della registrazione qualora non venissero introdotte credenziali
//           // valide durante la registrazione. Il runLater() serve ad assicuraci che questo codice venga eseguito
//           // solamente dopo aver scritto nei vari textField.

//           Platform.runLater(() -> {
//               BooleanBinding bb = new BooleanBinding() {
//                   {
//                       super.bind(
//                               emailText.textProperty(),
//                               usernameText.textProperty(),
//                               passwordField.textProperty(),
//                               birthdayPicker.valueProperty()
//                       );
//                   }

//                   @Override
//                   protected boolean computeValue() {
//                       return !(isGoodEmail && isGoodAge && isGoodUsername && isGoodPassword);
//                   }
//               };

//               buttonRegisterAccount.disableProperty().bind(bb);
//           });
//       }

//       private void updateLanguage() {
//           ResourceBundle bundle = null;
//           try {
//               System.out.println(SettingsHandler.getInstance().loginLanguage);
//               bundle = lanHandler.getBundle();
//           } catch (Exception e) {
//               alertHandler.createErrorAlert("Error in loading the language");
//           }
//           if (bundle != null) {
//               buttonRegisterAccount.setText(bundle.getString("registerButton"));
//               backButton.setText(bundle.getString("backButton"));
//               nameLabel.setText(bundle.getString("nameLabel"));
//               surnameLabel.setText(bundle.getString("surnameLabel"));
//               birthdayLabel.setText(bundle.getString("birthdayLabel"));
//           }
//       }
}
