package it.unical.demacs.informatica.mvcwallet.handler;

import java.sql.*;
import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SqlHandler {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private SqlHandler() {}
    private static final SqlHandler instance = new SqlHandler();
    public static SqlHandler getInstance() {
        return instance;
    }
    private Connection con;

    public Connection newConnection() {
        try {
            String url = "jdbc:sqlite:progettouid.db";
            // Effettua la connessione al database
            con = DriverManager.getConnection(url);
            if (con != null) System.out.println("Connection Succes");

        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return con;
    }

    public void closeConnection(Connection con){
        try {
            con.close();
            System.out.println("Connessione chiusa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkPassword(String username,String password) {
        try {
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT password FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (BCrypt.checkpw(password, rs.getString(1))) {
                closeConnection(con);
                return true;
            }else{
                closeConnection(con);
                return false;
            }

        }catch(SQLException e){
                e.printStackTrace();
        }
        closeConnection(con);
        return false;
    }


    public byte checkLogin(String username, String password) {
        // Esegue una query per il login di un utente.
        try {
            if (checkUsernameLogin(username)) {
                con = newConnection();
                PreparedStatement stmt1 = con.prepareStatement("SELECT password FROM users WHERE username = ?");
                stmt1.setString(1, username);
                ResultSet rs1 = stmt1.executeQuery();
                if(BCrypt.checkpw(password, rs1.getString(1))) {
                    closeConnection(con);
                    return 0;
                }
                else {
                    closeConnection(con);
                    return 2;
                }
            }
            closeConnection(con);
            return 2;
        } catch (SQLException e) {
            closeConnection(con);
            e.printStackTrace();
        }
        closeConnection(con);
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
                createAccountSettings(username);
                closeConnection(con);
                return true;
            } catch (SQLException e) {
                alertHandler.createErrorAlert("Errore nella registrazione");
            }
        return false;
    }

    private void createAccountSettings(String username) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement("INSERT INTO settings values (?,?,?,?,?,?,?)");
            s.setString(1, username);
            s.setString(2, "it");
            s.setString(3, "dark.css");
            s.setString(4, "HH:mm:ss");
            s.setString(5, "market");
            s.setInt(6, 0);
            s.setString(7, "eur");

            s.executeUpdate();
            closeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUsernameLogin(String username){
        try{
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT username From users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                if (rs.getString(1).equals(username)){
                    stmt.close();
                    closeConnection(con);
                    return true;
                }
            closeConnection(con);
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
                    closeConnection(con);
                    return true;
                }
            closeConnection(con);
        }catch(SQLException e){ e.getMessage(); }
        return false;
    }

    public boolean forgotPassword(String email, String newPassword) {
        // Esegue una query per il cambio password
        if(checkEmail(email)){
            try {
                con = newConnection();
                PreparedStatement stmt1 = con.prepareStatement("UPDATE users SET Password = ? WHERE Email = ?");
                stmt1.setString(1,BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
                stmt1.setString(2,email);
                stmt1.executeUpdate();
                closeConnection(con);
                return true;
            }catch(SQLException e){ e.getMessage(); }
        }
        return false;
    }

    public String[] getNameSurname(String username){
        try{
            con = newConnection();
            String [] array = new String[2];
            PreparedStatement stmt = con.prepareStatement("SELECT name, surname FROM users WHERE username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                closeConnection(con);
                return array;
            }
            closeConnection(con);
        }catch(SQLException e){
            e.getMessage();
        }
        closeConnection(con);
        return null;
    }

    public boolean checkUsername(String username){
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement("select username from users");
            ResultSet s1 = s.executeQuery();
            while(s1.next())
                if(s1.getString(1).equals(username)){
                    closeConnection(con);
                    return true;
                }
            closeConnection(con);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeUsername(String oldUsername, String newUsername){
        try{
            con = newConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            stm.setString(1, newUsername );
            stm.setString(2, oldUsername);
            if(stm.executeUpdate() == 1){
                closeConnection(con);
                return true;
            }

           if(stm.executeUpdate() == 0){
               closeConnection(con);
               return false;
           }
            closeConnection(con);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changeName(String newName,String username){
        try{
            con = newConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE users SET name = ? WHERE username = ?");
            stm.setString(1, newName );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                closeConnection(con);
                return true;
            }

            if(stm.executeUpdate() == 0){
                closeConnection(con);
                return false;
            }
            closeConnection(con);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean changeSurname(String surname,String username){
        try{
            con = newConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE users SET surname = ? WHERE username = ?");
            stm.setString(1, surname );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                closeConnection(con);
                return true;
            }

            if(stm.executeUpdate() == 0){
                closeConnection(con);
                return false;
            }

            closeConnection(con);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changePassword(String password, String username) {
        try{
            con = newConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            stm.setString(1, BCrypt.hashpw(password, BCrypt.gensalt(12)) );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                closeConnection(con);
                return true;
            }

            if(stm.executeUpdate() == 0){
                closeConnection(con);
                return false;
            }

            closeConnection(con);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String [] getSettingsQuery(String username){
        try{
            con = newConnection();
            String [] settings = new String[6];
            PreparedStatement s = con.prepareStatement("SELECT time, page, logged, theme, language, currency FROM settings WHERE username = ?");
            s.setString(1, username);

            ResultSet rs = s.executeQuery();
            settings[0] = rs.getString(1);
            settings[1] = rs.getString(2);
            settings[2] = rs.getString(3);
            settings[3] = rs.getString(4);
            settings[4] = rs.getString(5);
            settings[5] = rs.getString(6);

            closeConnection(con);
            return settings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSettingsQuery(String username, String time, String page, String logged, String theme, String language, String currency)  {
            try {
                con = newConnection();
                PreparedStatement s = con.prepareStatement("UPDATE settings SET time = ?,page = ?, " +
                        "logged = ?, theme = ?, language = ?, currency = ? WHERE username = ? ");
                s.setString(1, time);
                s.setString(2, page);
                s.setString(3, logged);
                s.setString(4, theme);
                s.setString(5, language);
                s.setString(6, currency);
                s.setString(7, username);
                s.executeUpdate();
                closeConnection(con);
            } catch (SQLException e) {
                System.out.println("Error: ChangeSettingQuery");
                e.printStackTrace();
                //Thread.sleep(retryDelayMillis);
            }
    }

    public void setLogutQuery(String username) throws InterruptedException {
        try {
            con = newConnection();
            PreparedStatement s = con.prepareStatement("UPDATE settings SET logged = ? WHERE username = ? ");
            s.setString(1, "0");
            s.setString(2, username);
            s.executeUpdate();
            closeConnection(con);
            } catch (SQLException e) {
                System.out.println("Error: Logout");
                e.printStackTrace();
            }
    }

    public void stayLoggedOfLogin(String username) throws InterruptedException {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement("UPDATE settings SET logged = ? WHERE username = ? ");
            s.setString(1, "1" );
            s.setString(2, username );
            s.executeUpdate();
            closeConnection(con);
        } catch (SQLException e) {
            System.out.println("Error: SqlHandler.java rows: 334-345");

        }
    }

    public boolean getEmail(String email) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement("SELECT email FROM users");
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                if(rs.getString(1).equals(email)){
                    closeConnection(con);
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection(con);
        return false;
    }

}