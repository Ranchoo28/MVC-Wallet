package it.unical.demacs.informatica.mvcwallet.handler;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class LoggedHandler {

    private static final LoggedHandler instance = new LoggedHandler();
    public static LoggedHandler getInstance() {
        return instance;
    }
    private LoggedHandler() {}

    public String stayLoggedReading(){
        String username;
        try {
            FileReader stream = new FileReader("stayLogged.txt");
            BufferedReader buff = new BufferedReader(stream);
            username = buff.readLine();
            buff.close();
            stream.close();
            return username;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void stayLoggedWriting(String username){
        try {
            FileWriter stream = new FileWriter("stayLogged.txt", false);
            PrintWriter file = new PrintWriter(stream);
            file.println(username);
            file.close();
            stream.close();
        }catch (IOException e){
            System.out.println("Errore nella scrittura del file");
        }
    }
}
