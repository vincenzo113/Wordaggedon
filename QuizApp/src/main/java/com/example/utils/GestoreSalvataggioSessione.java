package com.example.utils;

import com.example.exceptions.SessioneNonCaricataException;
import com.example.models.SessioneQuiz;

import java.io.*;

public class GestoreSalvataggioSessione {


    //Metodo per salvare la sessione su file , solo se la sessione non è già completata , quindi quando si va al riepilogo è già completata
    public static void salvaSessione(SessioneQuiz sessioneQuiz) {
        String filename = "salvataggio_" + sessioneQuiz.getUser().getUsername() + ".dat";
        if (!sessioneQuiz.isCompleta()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                objectOutputStream.writeObject(sessioneQuiz);
            } catch (IOException ex) {
            }
        }
    }


    //Metodo per leggere la sessione da file
    public static SessioneQuiz loadSessione(String username) throws SessioneNonCaricataException {
        String filename = "salvataggio_" + username + ".dat";
        SessioneQuiz sessioneQuiz = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            sessioneQuiz = (SessioneQuiz) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new SessioneNonCaricataException("File di salvataggio non trovato: " + filename);
        } catch (IOException e) {
            throw new SessioneNonCaricataException("Errore di I/O durante il caricamento della sessione");
        } catch (ClassNotFoundException e) {
            throw new SessioneNonCaricataException("Classe SessioneQuiz non trovata durante il caricamento");
        } catch (Exception e) {
            throw new SessioneNonCaricataException("Errore generico durante il caricamento della sessione");
        }
        System.out.println("Ho trovato la sessione: " + sessioneQuiz);
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