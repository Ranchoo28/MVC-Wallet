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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileController {
    @FXML
    private TextField usernameText, nameText, surnameText, passwordText;
    @FXML
    private Button usernameButton, nameButton, surnameButton, passwordButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheckBox;

    boolean isGoodUsername;
    boolean isGoodPassword;



    @FXML
    void changeVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            passwordText.setText(passwordField.getText());
            passwordText.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        passwordField.setText(passwordText.getText());
        passwordField.setVisible(true);
        passwordText.setVisible(false);
    }

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

    @FXML
    void onChangePasswordButtonClick() {
        if (SqlService.getIstance().serviceChangePassword(passwordText.getText(), usernameText.getText())) {
            SceneHandler.getInstance().createChangedAlert("password");
            SceneHandler.getInstance().createSideBar();
        }

    }


    @FXML
    void initialize() {

        passwordText.textProperty().addListener((observable, oldValue, newValue) -> {
                    // Controlla se la password rispetta il Regex
                    if (newValue.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@!%&£°#'?*=])[a-zA-Z0-9@!%&£°#'?*=]{8,}"))
                        isGoodPassword = true;
                    else
                        isGoodPassword = false;
                    checkPassword();
        });
        usernameText.setText(LoginController.username);
        usernameText.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() < 5)
                    isGoodUsername = false;
                else
                    isGoodUsername = true;
                checkUsername();
            }
        });
        String[] array = SqlHandler.getIstance().getNameSurname(LoginController.username);
        nameText.setText(array[0]);
        surnameText.setText(array[1]);
        //passwordText.setText(LoginController.);
        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                checkName();
            }
        });

        surnameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                checkSurname();
            }
        });

        passwordText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                checkPassword();
            }
        });

    }

    private void checkUsername() {
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(usernameButton.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return (!usernameText.getText().equals(LoginController.username)&& !isGoodUsername);
                }
            };
            usernameButton.disableProperty().bind(bb);
        });
    }

    private void checkName() {
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(nameButton.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return (nameText.getText().equals("prova"));
                }
            };
            nameButton.disableProperty().bind(bb);
        });
    }

    private void checkSurname() {
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(surnameButton.textProperty());
                }

                @Override
                protected boolean computeValue() {
                    return (surnameText.getText().equals("prova"));
                }
            };
            surnameButton.disableProperty().bind(bb);
        });
    }

    private void checkPassword() {
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(
                            passwordText.textProperty()
                    );
                }

                @Override
                protected boolean computeValue() {
                    return (passwordText.getText().equals("prova"));
                }
            };
            passwordButton.disableProperty().bind(bb);
        });

        }


}
