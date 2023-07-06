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
    private TextField usernameText, nameText, surnameText;

    @FXML
    private Button applyButton;

    @FXML
    private Button backButton;

    @FXML
    private Label checkLabel;
    boolean isGoodUsername, isGoodName, isGoodSurname;


    @FXML
    void onChangeUsernameButtonClick() {
        if (SqlService.getIstance().serviceChangeUsername(LoginController.username, usernameText.getText())) {
            LoginController.username = usernameText.getText();
            SceneHandler.getInstance().createChangedAlert("username");
            SceneHandler.getInstance().createSideBar();
        } else System.out.println("nome non cambiato");

    }

    @FXML
    void onChangeNameButtonClick() {
        if (SqlService.getIstance().serviceChangeName(nameText.getText(), usernameText.getText())) {
            SceneHandler.getInstance().createChangedAlert("nome");
            SceneHandler.getInstance().createSideBar();
        }

    }

    @FXML
    void onChangeSurnameButtonClick() {
        if (SqlService.getIstance().serviceChangeSurName(surnameText.getText(), usernameText.getText())) {
            SceneHandler.getInstance().createChangedAlert("cognome");
            SceneHandler.getInstance().createSideBar();
        }
    }


    private boolean isGoodApply(String username,String name, String surname){
        if ((username.length() >= 5 && !SqlHandler.getIstance().checkUsername(username))
                && (name.length()>=1 && !name.equals(nameText.getText()))
                && (surname.length()>=1 && !surname.equals(surnameText.getText())))
            return true;
        else return false;
    }



    @FXML
    void initialize() {
        applyButton.setDisable(true);
        nameSurnameArray = SqlHandler.getIstance().getNameSurname(LoginController.username);
        String[] array = SqlHandler.getIstance().getNameSurname(LoginController.username);
        nameText.setText(array[0]);
        surnameText.setText(array[1]);



        usernameText.setText(LoginController.username);
        usernameText.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(SqlHandler.getIstance().checkUsername(newValue));
                if (isGoodApply(usernameText.getText(), nameText.getText(), surnameText.getText())){
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




        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (isGoodApply(usernameText.getText(), nameText.getText(), surnameText.getText())){
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

        surnameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(usernameText.getText());
                System.out.println(SqlHandler.getIstance().checkUsername(usernameText.getText()));
                if (isGoodApply(usernameText.getText(), nameText.getText(), surnameText.getText())){
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
                    super.bind(usernameText.textProperty()
                               ,nameText.textProperty()
                               ,surnameText.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return  isGoodUsername || isGoodName  || isGoodSurname;
                }
            };
            applyButton.disableProperty().bind(bb);
        });
    }


}
