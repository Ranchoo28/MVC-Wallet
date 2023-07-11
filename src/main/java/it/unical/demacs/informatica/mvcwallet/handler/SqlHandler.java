package it.unical.demacs.informatica.mvcwallet.handler;

import java.sql.*;
import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SqlHandler {
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
                rs.close();
                stmt.close();
                closeConnection(con);
                return true;
            } else {
                rs.close();
                stmt.close();
                closeConnection(con);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
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
                    rs1.close();
                    stmt1.close();
                    closeConnection(con);
                    return 0;
                }
                else {
                    rs1.close();
                    stmt1.close();
                    closeConnection(con);
                    return 2;
                }
            }else{
                closeConnection(con);
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean registerAccount(String email, String username, String password, LocalDate birthday, String nome, String cognome) {
        // Esegue una query per la registrazione di un utente.
        if(checkEmail(email) || checkUsername(username)) return false;
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
                insertAccountSettings(username);
                insertAccountSpots(username);
                insertCustomTheme(username);
                stmt.close();
                closeConnection(con);
                return true;
            } catch (SQLException e) {
                throw new RuntimeException();
            }
    }

    private void insertAccountSettings(String username) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement(
                    "INSERT INTO settings (username, language, theme, time, page, logged, currency) VALUES (?,?,?,?,?,?,?)");
            s.setString(1, username);
            s.setString(2, "it");
            s.setString(3, "mvc.css");
            s.setString(4, "HH:mm:ss");
            s.setString(5, "market");
            s.setInt(6, 0);
            s.setString(7, "eur");
            s.executeUpdate();

            s.close();
            closeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insertAccountSpots(String username) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement(
                    "INSERT INTO spots (username,BTC, Ethereum, Solana, binance_coin) VALUES (?,?,?,?,?)");
            s.setString(1, username);
            s.setDouble(2, 0.00);
            s.setDouble(3,0.00  );
            s.setDouble(4,0.00  );
            s.setDouble(5,0.00  );
            s.executeUpdate();

            s.close();
            closeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public double [] getSpots(String username){
        try{
            double [] coins = new double[7];
            con = newConnection();
            PreparedStatement s = con.prepareStatement(
                    "SELECT BTC, Ethereum, Solana, Binance_coin FROM spots WHERE username = ?");
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                coins[0] = rs.getDouble(1);
                coins[1] = rs.getDouble(2);
                coins[2] = rs.getDouble(3);
                coins[3] = rs.getDouble(4);
            }
            rs.close();
            s.close();
            closeConnection(con);
            return coins;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertCustomTheme(String username) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement(
                    "INSERT INTO customTheme(mainBgc, secondBgc,hoverColor,buttonColor,borderColor,mainTxtColor,secondTxtColor,username) VALUES (?,?,?,?,?,?,?,?)");
            s.setString(1, "#ffffff");
            s.setString(2, "#ffffff");
            s.setString(3, "#ffffff");
            s.setString(4, "#ffffff");
            s.setString(5, "#ffffff");
            s.setString(6, "#ffffff");
            s.setString(7, "#ffffff");
            s.setString(8, username);


            s.executeUpdate();

            s.close();
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
                    rs.close();
                    stmt.close();
                    closeConnection(con);
                    return true;
                }
            rs.close();
            stmt.close();
            closeConnection(con);
        }catch(SQLException e){
            throw new RuntimeException();
        }
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
                    rs.close();
                    closeConnection(con);
                    return true;
                }

            rs.close();
            stmt.close();
            closeConnection(con);
        }catch(SQLException e){
            throw new RuntimeException();
        }
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

                stmt1.close();
                closeConnection(con);
                return true;
            }catch(SQLException e){
                throw new RuntimeException();
            }
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

            while(rs.next()){
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
            }


            rs.close();
            stmt.close();
            closeConnection(con);
            return array;

        }catch(SQLException e){
            throw new RuntimeException();
        }
    }

    public boolean checkUsername(String username) {
        try {
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT username From users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                if (rs.getString(1).equals(username)) {
                    stmt.close();
                    rs.close();
                    closeConnection(con);
                    return true;
                }

            stmt.close();
            rs.close();
            closeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return false;
    }




    public boolean changeName(String newName,String username){
        try{
            con = newConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE users SET name = ? WHERE username = ?");
            stm.setString(1, newName );
            stm.setString(2,username);
            if(stm.executeUpdate() == 1){
                stm.close();
                closeConnection(con);
                return true;
            }

            if(stm.executeUpdate() == 0){
                stm.close();
                closeConnection(con);
                return false;
            }
            stm.close();
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
                stm.close();
                closeConnection(con);
                return true;
            }

            if(stm.executeUpdate() == 0){
                stm.close();
                closeConnection(con);
                return false;
            }
            stm.close();
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
                stm.close();
                return true;
            }

            if(stm.executeUpdate() == 0){
                closeConnection(con);
                stm.close();
                return false;
            }
            stm.close();
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
            while(rs.next()){
                settings[0] = rs.getString(1);
                settings[1] = rs.getString(2);
                settings[2] = rs.getString(3);
                settings[3] = rs.getString(4);
                settings[4] = rs.getString(5);
                settings[5] = rs.getString(6);
            }


            rs.close();
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
            } catch (Exception e) {
                System.out.println("Errore nel cambio impostazioni. Verranno resettate quelle di defaut" + e);
                SettingsHandler.getInstance().defaultSettings();
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
                throw new RuntimeException();
            }
    }

    public void stayLoggedOfLogin(String username) {
        try{
            con = newConnection();
            PreparedStatement s = con.prepareStatement("UPDATE settings SET logged = ? WHERE username = ? ");
            s.setString(1, "1" );
            s.setString(2, username );
            s.executeUpdate();
            closeConnection(con);
        } catch (SQLException e) {
            closeConnection(con);
            System.out.println("Error: SqlHandler.java rows: 334-345 " + e);
            throw new RuntimeException();

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
            closeConnection(con);
            throw new RuntimeException(e);
        }
        closeConnection(con);
        return false;
    }

    public String [] getCustomTheme(String username){
        try{
            String [] colors = new String[7];
            con = newConnection();
            PreparedStatement s = con.prepareStatement(
                    "SELECT mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor FROM customTheme WHERE username = ?");
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                colors[0] = rs.getString(1);
                colors[1] = rs.getString(2);
                colors[2] = rs.getString(3);
                colors[3] = rs.getString(4);
                colors[4] = rs.getString(5);
                colors[5] = rs.getString(6);
                colors[6] = rs.getString(7);
            }
            rs.close();
            s.close();
            closeConnection(con);
            return colors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] getProfileInfo(String username){
        try{
            con = newConnection();
            String [] array = new String[4];
            PreparedStatement stmt = con.prepareStatement("SELECT name, surname, email, birthday FROM users WHERE username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                array[2] = rs.getString(3);
                array[3] = rs.getString(4);
            }
            rs.close();
            stmt.close();
            closeConnection(con);
            return array;
        }catch(SQLException e){
            throw new RuntimeException();
        }
    }


}