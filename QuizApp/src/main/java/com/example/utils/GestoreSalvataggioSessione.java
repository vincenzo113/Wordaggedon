package com.example.utils;

import com.example.exceptions.SessioneNonCaricataException;
import com.example.models.SessioneQuiz;

import java.io.*;

/**
 * Classe utility per la gestione del salvataggio, caricamento e cancellazione
 * delle sessioni di quiz su file.
 */
public class GestoreSalvataggioSessione {

    /**
     * Salva la sessione di quiz su file, solo se la sessione non è già completata.
     *
     * @param sessioneQuiz La sessione da salvare.
     */
    public static void salvaSessione(SessioneQuiz sessioneQuiz) {
        String filename = "salvataggio_" + sessioneQuiz.getUser().getUsername() + ".dat";
        if (!sessioneQuiz.isCompleta()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                objectOutputStream.writeObject(sessioneQuiz);
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Carica una sessione di quiz da file in base allo username fornito.
     *
     * @param username Lo username associato alla sessione da caricare.
     * @return La sessione caricata.
     * @throws SessioneNonCaricataException Se si verifica un errore durante il caricamento o se il file non è trovato.
     */
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
        return sessioneQuiz;
    }

    /**
     * Elimina il file di salvataggio della sessione associato allo username fornito.
     *
     * @param username Lo username della sessione da eliminare.
     */
    public static  void eliminaSessione(String username){
        String filename = "salvataggio_"+username+".dat";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}