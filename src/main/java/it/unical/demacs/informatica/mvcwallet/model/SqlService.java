package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SqlService {
    private static final SqlHandler sqlHandler = SqlHandler.getInstance();

    private static final SqlService instance = new SqlService();
    public static SqlService getInstance() { return instance; }

    // Executor service per l'esecuzione delle query
    public byte serviceLogin(String username, String password) {
        byte[] res = new byte[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.checkLogin(username, password));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public boolean serviceRegister(String email, String username, String password, LocalDate birthday, String nome, String cognome){
        final boolean[] c = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> c[0] = sqlHandler.registerAccount(email, username, password, birthday, nome, cognome));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return c[0];
    }

    public boolean serviceForgotPassword(String email, String password){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.forgotPassword(email, password));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public void serviceLogout(String username){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> {
            try {
                sqlHandler.setLogutQuery(username);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }

    public boolean serviceChangeName( String newName,String username){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.changeName(newName,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }
    public boolean serviceChangeSurName( String newSurName,String username){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.changeSurname(newSurName,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public boolean serviceChangePassword(String newPassword, String username) {
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.changePassword(newPassword,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

   public String [] serviceSettings(String username){
       var ref = new Object() {
           String[] settings = new String[4];
       };
       ExecutorService queryExe = Executors.newSingleThreadExecutor();
       Future<?> future = queryExe.submit(() -> ref.settings = sqlHandler.getSettingsQuery(username));

       try { future.get(); }
       catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
       finally { queryExe.shutdown(); }
       //System.out.println( "Settings: " + ref.settings[0] +  ref.settings[1] + ref.settings[2]);
       return ref.settings;
   }

   public void serviceChangeSetting(String username, String time, String page, String logged, String theme, String language, String currency){
       ExecutorService queryExe = Executors.newSingleThreadExecutor();
       System.out.println("tema " + theme);
       Future<?> future = queryExe.submit(() -> sqlHandler.setSettingsQuery(username, time, page, logged, theme, language, currency));

       try { future.get(); }
       catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
       finally { queryExe.shutdown(); }
   }

    public boolean getEmail(String email) {
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = sqlHandler.getEmail(email));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public String [] serviceGetCustomTheme(String username) {
        var ref = new Object() {
            String[] colors = new String[7];
        };

        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> ref.colors = SqlHandler.getInstance().getCustomTheme(username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return ref.colors;
    }

    public void serviceCustomThemeOnDB(String mainBgc, String secondBgc, String hoverColor, String buttonColor, String borderColor, String mainTxtColor, String secondTxtColor ){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> SqlHandler.getInstance().setCustomThemeOnDB(mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }

    public String serviceGetCustomThemeFontSize(){
        final String[] fontSize = new String[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> fontSize[0] = SqlHandler.getInstance().getCustomThemeFontSize());

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }

        return fontSize[0];
    }

    public void serviceSetCustomThemeFontSize(String fontSize){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> SqlHandler.getInstance().setCustomThemeFontSize(fontSize));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }
    public void serviceSpotBTC(String username,double amount){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> sqlHandler.setNewBTC(username,amount));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }
    public void serviceSpotETH(String username,double amount){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> sqlHandler.setNewETH(username,amount));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }
    public void serviceSpotSOL(String username,double amount){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> sqlHandler.setNewSOL(username,amount));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }
    public void serviceSpotBNB(String username,double amount){
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> sqlHandler.setNewBNB(username,amount));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
    }

}
