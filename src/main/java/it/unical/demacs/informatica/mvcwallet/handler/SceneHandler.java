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
        System.out.println(SceneHandler.class.getResource(css + settingsHandler.theme));
        for(String i: SettingsHandler.getInstance().themes)
            scene.getStylesheets().remove(String.valueOf(SceneHandler.class.getResource(css + i)));

        scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + settingsHandler.theme)));
    }

    public void uploadLanguage(){
        languageHandler.updateLanguage(settingsHandler.language);
    }

    public void init(Stage stage) throws IOException {
        // Crea lo stage iniziale
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Wallet Login");
            createLoginScene();
            stage.setResizable(true);
            stage.setScene(scene);

            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(800);
            stage.setHeight(600);

            stage.show();
            scene.setOnKeyPressed(key -> {
                if(key.getCode().equals(KeyCode.F11))
                    stage.setFullScreen(!stage.isFullScreen());
            });
        }
    }

    public void createSideBar() {
        try {
            Parent root = loadRootFromFXML(view + "sidebar-view.fxml");
            if (scene == null) scene = new Scene(root);
            else scene.setRoot(root);
            stage.setTitle("MVC Wallet");
            uploadTheme();
            uploadLanguage();
        } catch (IOException ignored) {
        }
    }


    public void createLoginScene() {
        // Crea la scena del login
        try {
            Parent root = loadRootFromFXML(view+"login-view.fxml");
            if(scene == null)
                scene = new Scene(root);
            else
                scene.setRoot(root);
            stage.setTitle("MVC Wallet login");
            scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + "dark.css")));
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
            if(stage.isFullScreen()) stage.setFullScreen(false);
        } catch (IOException ignored) {
        }
    }


}
