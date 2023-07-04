package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.model.CandleStickChart;
import it.unical.demacs.informatica.mvcwallet.model.DecimalAxisFormatter;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.*;

public class SceneHandler {

    private static final SceneHandler instance = new SceneHandler();
    private static final String view = "/it/unical/demacs/informatica/mvcwallet/view/";
    private Stage stage;
    private Scene scene;

    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {}

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));
        return fxmlLoader.load();
    }


    public void init(Stage stage) {
        // Crea lo stage iniziale
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Wallet Login");
            stage.setResizable(false);
            //createLoginScene();
            createSideBar();
            stage.setScene(scene);
            stage.show();
            scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource("/it/unical/demacs/informatica/mvcwallet/css/main.css").toExternalForm()));
            scene.setOnKeyPressed(key -> {
                if(key.getCode().equals(KeyCode.F11))
                    stage.setFullScreen(!stage.isFullScreen());
            });
        }
    }

    public void createSideBar() {
        // Crea la scena per l'homepage
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"main-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"main-view.fxml"));
            stage.setTitle("MVC Wallet");
            //scene.setRoot(loadRootFromFXML(view+"main-view.fxml"));
            if(stage.isFullScreen()) stage.setFullScreen(true);
            else {
                stage.setWidth(800);
                stage.setHeight(500);
            }

        } catch (IOException ignored) {
        }
    }

    public void createAccountSettingsScene() {
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"account-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"account-view.fxml"));
            //scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(view+"CSS/main.css")).toExternalForm());
            if(stage.isFullScreen()) stage.setFullScreen(true);
            else {
                stage.setWidth(800);
                stage.setHeight(500);
            }
            stage.setTitle("MVC Wallet account");

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
                scene = new Scene(loadRootFromFXML(view+"forgot-pass.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"forgot-pass.fxml"));
            stage.setTitle("MVC Wallet password dimenticata");
            stage.setMinWidth(400);
            stage.setMinHeight(250);
            stage.setWidth(400);
            if(stage.isFullScreen()) stage.setFullScreen(false);
            stage.setHeight(250);
        } catch (IOException ignored) {
        }
    }

    public void createSettingsScene() {
        // Crea la scena per la password dimenticata
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML(view+"settings-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML(view+"settings-view.fxml"));
            stage.setTitle("MVC Wallet impostazioni");
            stage.setMinWidth(600);
            stage.setMinHeight(450);
            stage.setWidth(600);
            stage.setHeight(450);
        } catch (IOException ignored) {
        }
    }

    public void createCandleStickChart(){
        CandleStickChart candleStickChart = new CandleStickChart("Mo vediamo", buildBars());
        scene.getStylesheets().add("it.unical.demacs.informatica.mvcwallet.css/CandleStickChartStyles.css");
        scene = new Scene(candleStickChart);

        candleStickChart.setYAxisFormatter(new DecimalAxisFormatter("#000.0"));
    }

    public List<BarData> buildBars() {
        double previousClose = 1850;


        final List<BarData> bars = new ArrayList<>();
        GregorianCalendar now = new GregorianCalendar();
        for (int i = 0; i < 26; i++) {
            double open = getNewValue(previousClose);
            double close = getNewValue(open);
            double high = Math.max(open + getRandom(),close);
            double low = Math.min(open - getRandom(),close);
            previousClose = close;

            BarData bar = new BarData((GregorianCalendar) now.clone(), open, high, low, close, 1);
            now.add(Calendar.MINUTE, 5);
            bars.add(bar);
        }
        return bars;
    }

    protected double getNewValue( double previousValue ) {
        int sign;

        if( Math.random() < 0.5 ) {
            sign = -1;
        } else {
            sign = 1;
        }
        return getRandom() * sign + previousValue;
    }

    protected double getRandom() {
        double newValue = 0;
        newValue = Math.random() * 10;
        return newValue;
    }


    // Creazione dei vari alert
    public void createErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        FontIcon icon = new FontIcon("mdi2a-alert");
        icon.setIconColor(Paint.valueOf("#ff3333")); // Rosso
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Errore");
        alert.setContentText(message);
        alert.show();
    }
    public void createLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2c-check-circle-outline");
        icon.setIconColor(Paint.valueOf("#4d79ff")); // Blu
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Login eseguito!");
        alert.setContentText("Accesso avvenuto.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createSideBar();
    }

    public void createRegistrationAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Informazione");
        alert.setContentText("Registrazione effettuata, controlla la tua posta per la mail. \n Puoi effettuare il login!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createSideBar();
    }

    public void createForgotPassAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Informazione");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createLoginScene();
    }

    public void createLogoutAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        FontIcon icon = new FontIcon("mdi2p-progress-question");
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Logut");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createLoginScene();
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }


    public void createAskForChangeUsernameAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Cambio nome");
        alert.setContentText("Sei sicuro di voler cambiare nome?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createChangedUsernameAlert();
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }

    public void createChangedUsernameAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Cambio nome");
        alert.setContentText("Nome cambiato con successo!");
        alert.show();
    }
}
