package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import java.io.IOException;
import java.util.*;

public class SceneHandler {


    private static final String view = PathHandler.getInstance().getPathOfView();
    private static final String css = PathHandler.getInstance().getPathOfCSS();
    private Stage stage;
    private Scene scene;

    private static final SceneHandler instance = new SceneHandler();
    public static SceneHandler getInstance() {
        return instance;
    }
    private SceneHandler() {}

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));
        return fxmlLoader.load();
    }

    public void uploadTheme(){
        scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + SettingsHandler.getInstance().theme)));
    }

    public void uploadLanguage(){
        LanguageHandler.getInstance().updateLanguage(SettingsHandler.getInstance().language);
    }

    public void init(Stage stage) {
        // Crea lo stage iniziale
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Wallet Login");
            stage.setResizable(true);
            createLoginScene();
            //createSideBar();
            stage.setScene(scene);
            stage.show();
            scene.setOnKeyPressed(key -> {
                if(key.getCode().equals(KeyCode.F11))
                    stage.setFullScreen(!stage.isFullScreen());
            });
        }
    }

    public void createSideBar() {
        try {
            stage.setTitle("MVC Wallet");
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            Parent root = loadRootFromFXML(view + "main-view.fxml");
            if (scene == null) {
                scene = new Scene(root);
            } else {
                scene.setRoot(root);
            }
            uploadTheme();
            uploadLanguage();
            if (stage.isFullScreen())
                stage.setFullScreen(true);
        } catch (IOException ignored) {
        }
    }


    public void createLoginScene() {
        // Crea la scena del login
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"login-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"login-view.fxml"));
            stage.setTitle("MVC Wallet login");
            if(stage.isFullScreen()) stage.setFullScreen(false);

            stage.setMinWidth(600);
            stage.setMinHeight(350);
            stage.setWidth(600);
            stage.setHeight(350);

            //scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + "mvc.css")));
            LanguageHandler.getInstance().updateLanguage("en");
        } catch (IOException ignored) {
        }
    }

    public void createChangePasswordFromForgot() {
        // Crea la scena del cambio password dal forgot
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"change-pass-forgot-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"change-pass-forgot-view.fxml"));
            stage.setTitle("MVC Wallet Change password");
            if(stage.isFullScreen()) stage.setFullScreen(false);

            stage.setMinWidth(500);
            stage.setMinHeight(300);
            stage.setWidth(500);
            stage.setHeight(300);
        } catch (IOException ignored) {
        }
    }

    public void createRegistrationScene() {
        // Crea la scena della registrazione
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"register-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"register-view.fxml"));
            stage.setTitle("MVC Wallet registrazione");
            stage.setMinWidth(600);
            stage.setMinHeight(500);
            stage.setWidth(600);
            stage.setHeight(500);
            if(stage.isFullScreen()) stage.setFullScreen(false);
        } catch (IOException ignored) {
        }
    }

    public void createForgotPasswordScene() {
        // Crea la scena per la password dimenticata
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"forgot-pass-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"forgot-pass-view.fxml"));
            stage.setTitle("MVC Wallet password dimenticata");
            stage.setMinWidth(400);
            stage.setMinHeight(250);
            stage.setWidth(400);
            if(stage.isFullScreen()) stage.setFullScreen(false);
            stage.setHeight(250);
        } catch (IOException ignored) {
        }
    }


}
