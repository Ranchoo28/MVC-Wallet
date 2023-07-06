package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SqlService {

    private SqlService() {}
    private static SqlService istance = new SqlService();
    public static SqlService getIstance() { return istance; }

    // Executor service per l'esecuzione delle query
    public byte serviceLogin(String username, String password) {
        byte[] res = new byte[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().checkLogin(username, password));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public boolean serviceRegister(String email, String username, String password, LocalDate birthday, String nome, String cognome){
        final boolean[] c = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> c[0] = SqlHandler.getIstance().registerAccount(email, username, password, birthday, nome, cognome));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return c[0];
    }

    public boolean serviceForgotPassword(String email, String password){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().forgotPassword(email, password));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public boolean serviceChangeUsername(String oldUsername, String newUsername){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().changeUsername(oldUsername, newUsername));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }
    public boolean serviceChangeName( String newName,String username){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().changeName(newName,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }
    public boolean serviceChangeSurName( String newSurName,String username){
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().changeSurname(newSurName,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

    public boolean serviceChangePassword(String newPassword, String username) {
        boolean[] res = new boolean[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = SqlHandler.getIstance().changePassword(newPassword,username));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }

   public String [] serviceSettings(String username){
       var ref = new Object() {
           String[] settings = new String[3];
       };
       ExecutorService queryExe = Executors.newSingleThreadExecutor();
       Future<?> future = queryExe.submit(() -> ref.settings = SqlHandler.getIstance().getSettingsQuery(username));

       try { future.get(); }
       catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
       finally { queryExe.shutdown(); }
       //System.out.println( "Settings: " + ref.settings[0] +  ref.settings[1] + ref.settings[2]);
       return ref.settings;

   }

}