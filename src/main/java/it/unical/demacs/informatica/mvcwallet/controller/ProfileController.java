package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ResourceBundle;

public class ProfileController {

    @FXML
    AnchorPane centerPage;
    @FXML
    private Label nameLabel, surnameLabel;
    @FXML
    private TextField usernameTextField, firstTextField, lastTextField;
    @FXML
    private Button saveButton, cancelButton, changePassButton;

    String [] nameSurnameArray;
    boolean isGoodUsername, isGoodName, isGoodSurname;

    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    String view = PathHandler.getInstance().getPathOfView();

    @FXML
    void onChangePasswordClick(){
        loadFXML("changepass-view.fxml");
    }
    @FXML
    void onCancelClick(){SceneHandler.getInstance().createSideBar();
    }
    @FXML
    void onSaveClick() {
        if (sqlService.serviceChangeUsername(LoginController.username, usernameTextField.getText())) {
            LoginController.username = usernameTextField.getText();
            alertHandler.createChangedAlert("Username");
            sceneHandler.createSideBar();
        } else System.out.println("username non cambiato");
        if (sqlService.serviceChangeName(firstTextField.getText(), usernameTextField.getText())) {
            alertHandler.createChangedAlert(languageHandler.getBundle().getString("nameLabel"));
            sceneHandler.createSideBar();
        } else System.out.println("nome non cambiato");
        if (sqlService.serviceChangeSurName(lastTextField.getText(), usernameTextField.getText())) {
            alertHandler.createChangedAlert(languageHandler.getBundle().getString("surnameLabel"));
            sceneHandler.createSideBar();
        } else System.out.println("cognome non cambiato");
    }


    public void loadFXML(String nomeFXML) {
        centerPage.getChildren().clear();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view + nomeFXML));
            AnchorPane pane = fxmlLoader.load();
            centerPage.getChildren().add(pane);
            AnchorPane.setTopAnchor(pane, 5.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
        } catch (Exception e){
            System.out.println("Error in ProfileController.java (rows: 78-68) " + e);
        }
    }

    @FXML
    void initialize() {
        updateLanguage();
        saveButton.setDisable(true);
        usernameTextField.setText(LoginController.username);
        nameSurnameArray = sqlHandler.getNameSurname(LoginController.username);
        String[] array = sqlHandler.getNameSurname(LoginController.username);
        firstTextField.setText(array[0]);
        lastTextField.setText(array[1]);
        addListenerUsername();
        addListenerName();
        addListenersurname();
    }

    private void addListenerUsername(){
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodUsername = !SqlHandler.getInstance().checkUsername(usernameTextField.getText())
                            && usernameTextField.getText().length()>=5
                            &&!usernameTextField.getText().equals(LoginController.username);
            System.out.println(SqlHandler.getInstance().checkUsername(usernameTextField.getText()));
            if (!isGoodUsername && !isGoodName && !isGoodUsername) {
                saveButton.setDisable(true);
            }else{
                saveButton.setDisable(false);
            }
        });
    }
    private void addListenerName(){
        firstTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodName = firstTextField.getText().length()>=2 && !firstTextField.getText().equals(nameSurnameArray[0]) ;

            if (!isGoodUsername&&!isGoodName&&!isGoodUsername) {
                saveButton.setDisable(true);
            }else{
                saveButton.setDisable(false);
            }
        });
    }
    private void addListenersurname(){
        lastTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodSurname = lastTextField.getText().length()>=2 && !lastTextField.getText().equals(nameSurnameArray[1]) ;


            if (!isGoodUsername&&!isGoodName&&!isGoodSurname) {
                saveButton.setDisable(true);
            }else{
                saveButton.setDisable(false);
            }
        });
    }


    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e){
            System.out.println("Error in ProfileController.java (rows: 166-171) " + e);
        }
        if(bundle!=null) {
            saveButton.setText(bundle.getString("applyButton"));
            cancelButton.setText(bundle.getString("backButton"));
            changePassButton.setText(bundle.getString("changePasswordButton"));
            nameLabel.setText(bundle.getString("nameLabel"));
            surnameLabel.setText(bundle.getString("surnameLabel"));
        } else {
            System.out.println("MarketController.java: bundle is null");
        }
    }
}
