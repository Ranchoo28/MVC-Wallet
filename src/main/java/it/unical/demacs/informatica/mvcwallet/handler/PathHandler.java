package it.unical.demacs.informatica.mvcwallet.handler;

public class PathHandler {

    private static final PathHandler instance = new PathHandler();

    public static PathHandler getInstance() {
        return instance;
    }
    String pathOfView = "/it/unical/demacs/informatica/mvcwallet/view/";
    String pathOfCSS = "/it/unical/demacs/informatica/mvcwallet/css/";
    String pathOfFont = "/it/unical/demacs/informatica/mvcwallet/font/";
    String pathOfLanguage = "/it/unical/demacs/informatica/mvcwallet/language/LAN_";
    String pathOfLogo = "/it/unical/demacs/informatica/mvcwallet/Logo/MVC-NOBG.png"; //MVC.png"; //MvcWhite.png";

    public String getPathOfView() {
        return pathOfView;
    }
    public String getPathOfCSS() {
        return pathOfCSS;
    }
    public String getPathOfLanguage() {
        return pathOfLanguage;
    }
    public String getPathOfFont(){
        return pathOfFont;
    }
    public String getPathOfLogo() { return pathOfLogo; }
}
