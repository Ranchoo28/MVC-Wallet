package it.unical.demacs.informatica.mvcwallet.handler;

public class PathHandler {

    private static final PathHandler instance = new PathHandler();

    public static PathHandler getInstance() {
        return instance;
    }

    String pathOfView = "/it/unical/demacs/informatica/mvcwallet/view/";
    String pathOfCSS = "/it/unical/demacs/informatica/mvcwallet/css/";

    public String getPathOfView() {
        return pathOfView;
    }

    public String getPathOfCSS() {
        return pathOfCSS;
    }
}
