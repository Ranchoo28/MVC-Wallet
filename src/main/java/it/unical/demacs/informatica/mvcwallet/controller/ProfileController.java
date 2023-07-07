package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.PathHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProfileController {
    String [] nameSurnameArray;
    @FXML
    private TextField usernameTextField, firstTextField, lastTextField;

    @FXML
    private Button saveButton, cancelButton;

    boolean isGoodUsername, isGoodName, isGoodSurname;
    @FXML
    void onChangePasswordClick() {


    }
    @FXML
    void onCancelClick(){
    }
    @FXML
    void onSaveClick() {
        if (SqlService.getIstance().serviceChangeUsername(LoginController.username, usernameTextField.getText())) {
            LoginController.username = usernameTextField.getText();
            SceneHandler.getInstance().createChangedAlert("username");
            SceneHandler.getInstance().createSideBar();
        } else System.out.println("username non cambiato");
        if (SqlService.getIstance().serviceChangeName(firstTextField.getText(), usernameTextField.getText())) {
            SceneHandler.getInstance().createChangedAlert("nome");
            SceneHandler.getInstance().createSideBar();
        } else System.out.println("nome non cambiato");
        if (SqlService.getIstance().serviceChangeSurName(lastTextField.getText(), usernameTextField.getText())) {
            SceneHandler.getInstance().createChangedAlert("cognome");
            SceneHandler.getInstance().createSideBar();
        } else System.out.println("cognome non cambiato");
    }


    private boolean isGoodApply(String username,String name, String surname){
        return (username.length() >= 5 && !SqlHandler.getInstance().checkUsername(username))
                && (name.length() >= 1 && !name.equals(firstTextField.getText()))
                && (surname.length() >= 1 && !surname.equals(lastTextField.getText()));
    }

    public void loadFXML(String nomeFXML) throws IOException {
        centerPage.getChildren().clear();

        String path = PathHandler.getInstance().getPathOfView();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path + nomeFXML));
        AnchorPane pane = fxmlLoader.load();

        centerPage.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 5.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
    }

    @FXML
    void initialize() {
        saveButton.setDisable(true);
        nameSurnameArray = SqlHandler.getInstance().getNameSurname(LoginController.username);
        String[] array = SqlHandler.getInstance().getNameSurname(LoginController.username);
        firstTextField.setText(array[0]);
        lastTextField.setText(array[1]);



        usernameTextField.setText(LoginController.username);
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(SqlHandler.getInstance().checkUsername(newValue));
            isGoodUsername = isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText());
            checkUsername();

            /*(newValue.length() < 5 || SqlHandler.getIstance().checkUsername(newValue))
                isGoodUsername = false;
            else
                isGoodUsername = true;
            checkUsername();
            System.out.println(SqlHandler.getIstance().checkUsername(usernameText.getText()));

             */
        });




        firstTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            isGoodName = isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText());
            checkUsername();

            /*
            if(newValue.equals(nameSurnameArray[0]) || newValue.length() < 1 ||
                    SqlHandler.getIstance().checkUsername(usernameText.getText()) ||
                    usernameText.getText().length() < 5)

                isGoodName = false;
            else
                isGoodName = true;

             */
        });

        lastTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(usernameTextField.getText());
            System.out.println(SqlHandler.getInstance().checkUsername(usernameTextField.getText()));
            isGoodSurname = isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText());
            checkUsername();
            /*
            if(newValue.equals(nameSurnameArray[1]) || newValue.length() < 1 ||
                    SqlHandler.getIstance().checkUsername(usernameText.getText())||
                    usernameText.getText().length() < 5)
                isGoodSurname = false;
            else
                isGoodSurname = true;

             */
            checkUsername();
        });


    }

    private void checkUsername() {
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(usernameTextField.textProperty()
                               ,firstTextField.textProperty()
                               ,lastTextField.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return  isGoodUsername || isGoodName  || isGoodSurname;
                }
            };
            saveButton.disableProperty().bind(bb);
        });
    }
}
