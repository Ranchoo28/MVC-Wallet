package it.unical.demacs.informatica.mvcwallet.handler;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class SQLHandler {
    private Connection con = null;
    final private String url = "jdbc:mysql://localhost:3306/progettouid.db";
    final private String user = "root";
    final private String password = "supersium";

    private SQLHandler() {
    }

    private static SQLHandler istance = new SQLHandler();

    public static SQLHandler getIstance() {
        return istance;
    }



    public Connection newConnection() {
        try {
            // $12$o5oojaSJOEk1cmysAwW4BedYRzoXIO388IznZ7tXsgYM.A7vckXhO
            String url = "jdbc:sqlite:progettouid.db";
            Class.forName("org.sqlite.JDBC");
            // Effettua la connessione al database
            con = DriverManager.getConnection(url);
            //if (con != null) System.out.println("Connessione avvenuta con successo");

        } catch (SQLException e) {
            System.out.println("Non connesso" + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public void createTable(){
        try {
            System.out.println("Eseguo query");
            Connection con = newConnection();
            /*
            PreparedStatement stmt = con.prepareStatement(
                    "CREATE TABLE `users` (" +
                    "  `Username` varchar(45) NOT NULL," +
                    "  `Password` varchar(70) NOT NULL," +
                    "  `Email` varchar(45) NOT NULL," +
                    "  `DataNascita` date NOT NULL," +
                    "  `Nome` varchar(45) NOT NULL," +
                    "  `Cognome` varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`Username`)"
                    );

             */
            String query = "CREATE TABLE `users` (" +
                    "  `Username` varchar(45) NOT NULL," +
                    "  `Password` varchar(70) NOT NULL," +
                    "  `Email` varchar(45) NOT NULL," +
                    "  `DataNascita` date NOT NULL," +
                    "  `Nome` varchar(45) NOT NULL," +
                    "  `Cognome` varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`Username`)";
            Statement stmt = con.createStatement();
            stmt.execute(query);
            //stmt.close();
        }catch (SQLException e){
            e.getErrorCode();
        }
    }

    public void closeConnection (Connection con){
        // Esegue la disconnessione dal database
       try{
           con.close();
       }catch (SQLException e){ e.getMessage(); }
    }

    public byte checkLogin(String username, String password) {
        // Esegue una query per il login di un utente.
        try {
            boolean UsernameCorrect = false;
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT Username FROM users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getNString(1).equals(username)) {
                    UsernameCorrect = true;
                    break;
                } else {
                    closeConnection(con);
                    return 1;
                }
            }

            if (UsernameCorrect) {
                PreparedStatement stmt1 = con.prepareStatement("SELECT Password FROM users WHERE Username = ?");
                stmt1.setString(1, username);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    if(BCrypt.checkpw(password, rs1.getNString(1))) {
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
        try {
            java.util.Date date =
                    java.util.Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
            stmt.setString(3, email);
            stmt.setObject(4, sqlDate);
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

    public boolean checkEmail(String email){
        // Esegue una query per vedere se la mial inserita esiste nel database. Questo metodo viene sfruttato
        // qualora un utente si fosse dimenticato la password
        try{
            con = newConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT Email From users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                if (rs.getNString(1).equals(email)){
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
            PreparedStatement stmt = con.prepareStatement("SELECT Nome, Cognome FROM users WHERE Username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                array[0] = rs.getNString(1);
                array[1] = rs.getNString(2);
                return array;
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return null;
    }
}