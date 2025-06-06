package com.example.utils;

import com.example.models.SessioneQuiz;

import java.io.*;

public class GestoreSalvataggioSessione {


    //Metodo per salvare la sessione su file
    public static void salvaSessione(SessioneQuiz sessioneQuiz){
        String filename = "salvataggio_"+sessioneQuiz.getUser().getUsername()+".dat";
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))){
            objectOutputStream.writeObject(sessioneQuiz);
        }catch(IOException ex){}
    }


    //Metodo per leggere la sessione da file
    public static SessioneQuiz loadSessione(String username){
        String filename = "salvataggio_"+username+".dat";
        SessioneQuiz sessioneQuiz = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            sessioneQuiz = (SessioneQuiz) ois.readObject();
        }catch(Exception ex){}
        return sessioneQuiz;
    }


    public static  void eliminaSessione(String username){
        String filename = "salvataggio_"+username+".dat";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
