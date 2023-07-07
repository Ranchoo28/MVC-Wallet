package it.unical.demacs.informatica.mvcwallet.handler;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LoggedHandler {

    private static final LoggedHandler instance;

    static {
        try {
            instance = new LoggedHandler();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static LoggedHandler getInstance() {
        return instance;
    }
    private LoggedHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {}

    private  SecretKeySpec secretKey;
    private final String myKey = "MVC-Wallet";
    byte [] key;

    // Trasforma la chiave in una SecretKeySpec (con l'algoritmo di hashing SHA-512)
    public void setKey(String myKey) throws NoSuchAlgorithmException {

        key = myKey.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        secretKey = new SecretKeySpec(key, "AES");
    }

    // Crypta l'username
    public String encryptUsername(String username, String sec){
        try {
            setKey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(username.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            System.out.println("Error: LoggedHandler.java rows: 44-52");
            System.out.println(e);
        }
        return null;
    }

    // Decrypta username
    public String decryptUsername(String usernameCrypyted, String sec){
        try {
            setKey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(usernameCrypyted)));
        }catch (Exception e){
            System.out.println("Error: LoggedHandler.java rows: 58-66");
            System.out.println(e);
        }
        return null;
    }

    // Legge sul file di testo su cui è salvato l'username
    public String stayLoggedReading(){
        try {
            FileReader stream = new FileReader("stayLogged.txt");
            BufferedReader buff = new BufferedReader(stream);
            return decryptUsername(buff.readLine(), myKey);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    // Scrive sul file di testo in cui verrà salvato il nome
    public void stayLoggedWriting(String username){
        try {
            FileWriter stream = new FileWriter("stayLogged.txt", false);
            PrintWriter file = new PrintWriter(stream);

            file.println(encryptUsername(username, myKey));
            file.close();
            stream.close();
        }catch (IOException e){
            System.out.println("Error: LoggedHandler.java rows: 84-93");
        }
    }
}
