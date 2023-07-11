package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;

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
    private TextField usernameTextField, firstTextField, lastTextField,emailTextField,birthdayTextField;
    @FXML
    private Button saveButton, cancelButton, changePassButton;

    boolean  isGoodName, isGoodSurname;
    private final RegexHandler regexHandler = RegexHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    String view = PathHandler.getInstance().getPathOfView();
    String [] profileInfoArray =sqlHandler.getProfileInfo(LoginController.username);


    @FXML
    void onChangePasswordClick(){
        loadFXML("changepass-view.fxml");
    }
    @FXML
    void onCancelClick(){SceneHandler.getInstance().createSideBar();
    }
    @FXML
    void onSaveClick() {

        if (sqlService.serviceChangeName(firstTextField.getText(), usernameTextField.getText())) {
            sceneHandler.createSideBar();
        } else System.out.println("nome non cambiato");
        if (sqlService.serviceChangeSurName(lastTextField.getText(), usernameTextField.getText())) {
            sceneHandler.createSideBar();
        } else System.out.println("cognome non cambiato");
        alertHandler.createChangedAlert();

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
        birthdayTextField.setDisable(true);
        emailTextField.setDisable(true);
        usernameTextField.setDisable(true);
        usernameTextField.setText(LoginController.username);
        firstTextField.setText(profileInfoArray[0]);
        lastTextField.setText(profileInfoArray[1]);
        emailTextField.setText(profileInfoArray[2]);
        birthdayTextField.setText(profileInfoArray[3]);
        addListenerName();
        addListenerSurname();
    }


    private void addListenerName(){
        firstTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodName =  newValue.matches(regexHandler.regexFirstLast) && !firstTextField.getText().equals(profileInfoArray[0]) ;
            if(firstTextField.getText().length()>=2 && lastTextField.getText().length()>=2) {
                saveButton.setDisable(!isGoodName && !isGoodSurname);
            }else{
                saveButton.setDisable(true);
            }
        });
    }
    private void addListenerSurname(){
        lastTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodSurname = newValue.matches(regexHandler.regexFirstLast) && !lastTextField.getText().equals(profileInfoArray[1]) ;
            if(firstTextField.getText().length()>=2 && lastTextField.getText().length()>=2) {

                saveButton.setDisable(!isGoodName && !isGoodSurname);
            }else{
                saveButton.setDisable(true);
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
