package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController {
    String [] nameSurnameArray;
    @FXML
    private TextField usernameTextField, firstTextField, lastTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPassCheckBox;

    @FXML
    private Button saveButton, cancelButton;

    boolean isGoodUsername, isGoodName, isGoodSurname;


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
        if ((username.length() >= 5 && !SqlHandler.getIstance().checkUsername(username))
                && (name.length()>=1 && !name.equals(firstTextField.getText()))
                && (surname.length()>=1 && !surname.equals(lastTextField.getText())))
            return true;
        else return false;
    }



    @FXML
    void initialize() {
        saveButton.setDisable(true);
        nameSurnameArray = SqlHandler.getIstance().getNameSurname(LoginController.username);
        String[] array = SqlHandler.getIstance().getNameSurname(LoginController.username);
        firstTextField.setText(array[0]);
        lastTextField.setText(array[1]);



        usernameTextField.setText(LoginController.username);
        usernameTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(SqlHandler.getIstance().checkUsername(newValue));
                if (isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText())){
                    isGoodUsername = true;
                }else{
                    isGoodUsername = false;
                }
                checkUsername();

                /*(newValue.length() < 5 || SqlHandler.getIstance().checkUsername(newValue))
                    isGoodUsername = false;
                else
                    isGoodUsername = true;
                checkUsername();
                System.out.println(SqlHandler.getIstance().checkUsername(usernameText.getText()));

                 */
            }
        });




        firstTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText())){
                    isGoodName = true;
                }else{
                    isGoodName = false;
                }
                checkUsername();

                /*if(newValue.equals(nameSurnameArray[0]) || newValue.length() < 1 ||
                        SqlHandler.getIstance().checkUsername(usernameText.getText()) ||
                        usernameText.getText().length() < 5)

                    isGoodName = false;
                else
                    isGoodName = true;

                 */
            }
        });

        lastTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(usernameTextField.getText());
                System.out.println(SqlHandler.getIstance().checkUsername(usernameTextField.getText()));
                if (isGoodApply(usernameTextField.getText(), firstTextField.getText(), lastTextField.getText())){
                    isGoodSurname = true;
                }else{
                    isGoodSurname = false;
                }
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
            }
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
