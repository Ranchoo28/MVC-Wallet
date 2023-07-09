package it.unical.demacs.informatica.mvcwallet.handler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
public class SceneHandler {
    private Stage stage;
    private Scene scene;
    private static final String view = PathHandler.getInstance().getPathOfView();
    private static final String css = PathHandler.getInstance().getPathOfCSS();
    private static final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private static final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private static final LoggedHandler loggedHandler = LoggedHandler.getInstance();

    private static final SceneHandler instance = new SceneHandler();
    public static SceneHandler getInstance() {
        return instance;
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));
        return fxmlLoader.load();
    }

    public void uploadTheme(){
        scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + settingsHandler.theme)));
    }

    public void uploadLanguage(){
        languageHandler.updateLanguage(settingsHandler.language);
    }

    public void init(Stage stage) {
        // Crea lo stage iniziale
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Wallet Login");
            stage.setResizable(true);
            createLoginScene();

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
            Parent root = loadRootFromFXML(view + "main-view.fxml");
            if (scene == null) scene = new Scene(root);
            else scene.setRoot(root);

            stage.setTitle("MVC Wallet");
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);

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

            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);

            //scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + "mvc.css")));
            languageHandler.updateLanguage("en");
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
            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("changePasswordTitleScene"));
            if(stage.isFullScreen()) stage.setFullScreen(false);

            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);
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
            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("registrationTitleScene"));
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);
            if(stage.isFullScreen()) stage.setFullScreen(false);
        } catch (IOException ignored) {
        }
    }

    public void createForgotPasswordScene() {
        // Crea la scena per la password dimenticata
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view + "forgot-pass-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view + "forgot-pass-view.fxml"));

            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("changePasswordTitleScene"));
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);
            if(stage.isFullScreen()) stage.setFullScreen(false);
        } catch (IOException ignored) {
        }
    }


}
