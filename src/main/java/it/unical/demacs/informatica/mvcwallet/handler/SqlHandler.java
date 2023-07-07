package it.unical.demacs.informatica.mvcwallet.handler;

import java.sql.*;
import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SqlHandler {

    private SqlHandler() {}
    private Connection con;
    private static final SqlHandler instance = new SqlHandler();
    public static SqlHandler getInstance() {
        return instance;
    }

    public Connection newConnection() {
        try {
            String url = "jdbc:sqlite:progettouid.db";
            Class.forName("org.sqlite.JDBC");
            // Effettua la connessione al database
            con = DriverManager.getConnection(url);
            //if (con != null) System.out.println("Connessione avvenuta con successo");

        } catch (SQLException e) {
            //System.out.println("Non connesso" );
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    protected void closeConnection (Connection con){
        // Esegue la disconnessione dal database
       try{
           con.close();
       }catch (SQLException e){ e.getMessage(); }
    }

    public byte checkLogin(String username, String password) {
        // Esegue una query per il login di un utente.
        try {
            boolean UsernameCorrect;
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT username FROM users WHERE username = ?");
            if (stmt.execute()) UsernameCorrect=true;
            else return 1;
            if (UsernameCorrect) {
                PreparedStatement stmt1 = con.prepareStatement("SELECT password FROM users WHERE username = ?");
                stmt1.setString(1, username);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    if(BCrypt.checkpw(password, rs1.getString(1))) {
                        stmt.close();
                        stmt1.close();
                        return 0;
                    }
                    else{
                        stmt.close();
                        stmt1.close();
                        return 2;
                    }
                }
                stmt1.execute();
                stmt1.close();
            }
            stmt.execute();
            stmt.close();
            closeConnection(con);
            return 2;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 3;
    }

    public boolean registerAccount(String email, String username, String password, LocalDate birthday, String nome, String cognome) {
        // Esegue una query per la registrazione di un utente.
        if(checkEmail(email) && checkUsername(username)) return false;
        else
            try {
                con = newConnection();
                PreparedStatement stmt = con.prepareStatement("INSERT INTO users values (?, ?, ?, ?, ?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
                stmt.setString(3, email);
                stmt.setObject(4, birthday);
                stmt.setString(5, nome);
                stmt.setString(6, cognome);
                stmt.executeUpdate();
                stmt.close();
                return true;
            } catch (SQLException e) {
                SceneHandler.getInstance().createErrorAlert("Errore nella registrazione");
            }
        return false;
    }

    public boolean checkUsernameLogin(String username){
        try{
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT username From users WHERE username = ?");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                if (rs.getString(1).equals(username)){
                    stmt.close();
                    return true;
                }
            stmt.close();
            con.close();
        }catch(SQLException e){ e.getMessage(); }
        return false;
    }

    public boolean checkEmail(String email){
        // Esegue una query per vedere se la mail inserita esiste nel database. Questo metodo viene sfruttato
        // qualora un utente si fosse dimenticato la password

        try{
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT email From users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                if (rs.getString(1).equals(email)){
                    stmt.close();
                    return true;
                }
            stmt.close();
            con.close();
        }catch(SQLException e){ e.getMessage(); }
        return false;
    }

    public boolean forgotPassword(String email, String newPassword) {
        // Esegue una query per il cambio password
        if(checkEmail(email)){
            try {
                PreparedStatement stmt1 = con.prepareStatement("UPDATE users SET Password = ? WHERE Email = ?");
                stmt1.setString(1,BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
                stmt1.setString(2,email);
                stmt1.executeUpdate();
                stmt1.close();
                return true;
            }catch(SQLException e){ e.getMessage(); }
        }
        return false;
    }

    public String[] getNameSurname(String username){
        try{
            String [] array = new String[2];
            PreparedStatement stmt = con.prepareStatement("SELECT name, surname FROM users WHERE username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                return array;
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return null;
    }

    public boolean checkUsername(String username){
        try{
            PreparedStatement s = con.prepareStatement("select username from users");
            ResultSet s1 = s.executeQuery();
            while(s1.next())
                if(s1.getString(1).equals(username))
                    return true;
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeUsername(String oldUsername, String newUsername){
        try{
            PreparedStatement stm = con.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            stm.setString(1, newUsername );
            stm.setString(2, oldUsername);
            if(stm.executeUpdate() == 1){
                stm.close();
                return true;
            }

           if(stm.executeUpdate() == 0){
               stm.close();
               return false;
           }

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeName(String newName,String username){
        try{
            PreparedStatement stm = con.prepareStatement("UPDATE users SET name = ? WHERE username = ?");
            stm.setString(1, newName );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                stm.close();
                return true;
            }

            if(stm.executeUpdate() == 0){
                stm.close();
                return false;
            }

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean changeSurname(String surname,String username){
        try{
            PreparedStatement stm = con.prepareStatement("UPDATE users SET surname = ? WHERE username = ?");
            stm.setString(1, surname );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                stm.close();
                return true;
            }

            if(stm.executeUpdate() == 0){
                stm.close();
                return false;
            }

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changePassword(String Password, String username) {
        try{
            PreparedStatement stm = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            stm.setString(1, Password );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                stm.close();
                return true;
            }

            if(stm.executeUpdate() == 0){
                stm.close();
                return false;
            }

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String [] getSettingsQuery(String username){
        try{
            String [] settings = new String[3];
            PreparedStatement s = con.prepareStatement("SELECT time,page,logged FROM settings WHERE username = ?");
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                settings[0] = rs.getString(1);
                settings[1] = rs.getString(2);
                settings[2] = rs.getString(3);
            }

            return settings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSettingsQuery(String username, String time, String page, String logged){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE settings SET time = ?,page = ?, logged = ? WHERE username = ? ");
            s.setString(1, time );
            s.setString(2, page );
            s.setString(3, logged);
            s.setString(4, username );
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLogutQuery(String username){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE settings SET logged = ? WHERE username = ? ");
            s.setString(1, "0" );
            s.setString(2, username );
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            System.out.println("Error: SqlHandler.java rows: 296-305");
            e.printStackTrace();
        }
    }

    public void stayLoggedOfLogin(String username){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE settings SET logged = ? WHERE username = ? ");
            s.setString(1, "1" );
            s.setString(2, username );
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            System.out.println("Error: SqlHandler.java rows: 309-318");
            e.printStackTrace();
        }
    }
}