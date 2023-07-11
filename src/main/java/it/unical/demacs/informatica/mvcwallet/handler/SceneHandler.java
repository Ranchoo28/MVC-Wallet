package it.unical.demacs.informatica.mvcwallet.handler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneHandler {
    private Stage stage, stage1;
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
        //System.out.println(SceneHandler.class.getResource(css + settingsHandler.theme));
        for(String i: SettingsHandler.getInstance().themes)
            scene.getStylesheets().remove(String.valueOf(SceneHandler.class.getResource(css + i)));
        scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + settingsHandler.theme)));
    }

    public void uploadLanguage(){
        languageHandler.updateLanguage(settingsHandler.language);
    }
    public void uploadLoginLanguage(){ languageHandler.updateLanguage(settingsHandler.loginLanguage); }

    public void init(Stage stage){
        // Crea lo stage iniziale
        try {
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
                    if (key.getCode().equals(KeyCode.F11))
                        stage.setFullScreen(!stage.isFullScreen());
                });

            }
        } catch (Exception e){
            System.out.println("Error in SceneHandler.java (rows: 42-63) " + e);
        }
    }

    public void openCAT() throws IOException {
        stage1 = new Stage();
        Scene scene1 = new Scene(loadRootFromFXML(view + "color-picker-view.fxml"));
        scene1.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + settingsHandler.theme)));
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.setScene(scene1);
        stage1.setResizable(false);
        stage1.show();
    }
    public void closeCAT(){
        stage1.close();
    }

    public void createSideBar() {
        try {
            uploadLanguage();
            Parent root = loadRootFromFXML(view + "sidebar-view.fxml");
            if (scene == null) scene = new Scene(root);
            else scene.setRoot(root);
            stage.setTitle("MVC Wallet");
            uploadTheme();

        } catch (Exception e) {
            System.out.println("Error in SceneHandler.java (rows: 67-76) " + e);
        }
    }

    public void createLoginScene() {
        // Crea la scena del login
        try {
            uploadLoginLanguage();
            Parent root = loadRootFromFXML(view + "login-view.fxml");
            if(scene == null) scene = new Scene(root);
            else scene.setRoot(root);
            stage.setTitle("MVC Wallet login");
            for(String i: SettingsHandler.getInstance().themes)
                scene.getStylesheets().remove(String.valueOf(SceneHandler.class.getResource(css + i)));
            scene.getStylesheets().add(String.valueOf(SceneHandler.class.getResource(css + "blue.css")));
        } catch (Exception e) {
            System.out.println("Error in SceneHandler.java (rows: 82-93) " + e);
        }
    }

    public void createChangePasswordFromForgot() {
        // Crea la scena del cambio password dal forgot
        try {
            uploadLoginLanguage();
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"change-pass-forgot-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"change-pass-forgot-view.fxml"));

            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("changePasswordTitleScene"));
        } catch (Exception e) {
            System.out.println("Error in SceneHandler.java (rows: 98-107) " + e);
        }
    }

    public void createRegistrationScene() {
        // Crea la scena della registrazione
        try {
            uploadLoginLanguage();
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"register-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"register-view.fxml"));

            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("registrationTitleScene"));
        } catch (Exception e) {
            System.out.println("Error in SceneHandler.java (rows: 112-121) " + e);
        }
    }

    public void createForgotPasswordScene() {
        // Crea la scena per la password dimenticata
        try {
            uploadLoginLanguage();
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view + "token-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view + "token-view.fxml"));

            stage.setTitle("MVC Wallet " + languageHandler.getBundle().getString("changePasswordTitleScene"));

        } catch (Exception e) {
            System.out.println("Error in SceneHandler.java (rows: 126-135) " + e);
        }
    }
}
