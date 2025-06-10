package com.example.utils;

import com.example.exceptions.SessioneNonCaricataException;
import com.example.models.SessioneQuiz;

import java.io.*;

/**
 * Questa classe utility gestisce il salvataggio e il caricamento delle sessioni di quiz
 * su file, utilizzando la serializzazione di oggetti Java.
 * Permette agli utenti di riprendere una sessione non completata.
 */
public class GestoreSalvataggioSessione {

    /**
     * Salva l'oggetto {@link SessioneQuiz} fornito su un file.
     * La sessione viene salvata solo se non è già contrassegnata come "completa".
     * Il nome del file è formato da "salvataggio_" seguito dallo username dell'utente e dall'estensione ".dat".
     *
     * @param sessioneQuiz La {@link SessioneQuiz} da salvare.
     */
    public static void salvaSessione(SessioneQuiz sessioneQuiz) {
        String filename = "salvataggio_" + sessioneQuiz.getUser().getUsername() + ".dat";
        // Salva la sessione solo se non è già stata completata
        if (!sessioneQuiz.getIsCompleta()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                objectOutputStream.writeObject(sessioneQuiz);
            } catch (IOException ex) {
                // In un'applicazione reale, qui si dovrebbe loggare l'errore per debugging e notificarlo all'utente.
                System.err.println("Errore I/O durante il salvataggio della sessione per l'utente " + sessioneQuiz.getUser().getUsername() + ": " + ex.getMessage());
            }
        }
    }

<<<<<<< Updated upstream

    //Metodo per leggere la sessione da file
    public static SessioneQuiz loadSessione(String username) throws SessioneNonCaricataException {
=======
    /**
     * Carica una sessione di quiz da un file associato a un determinato username.
     * Il nome del file è formato da "salvataggio_" seguito dallo username e dall'estensione ".dat".
     *
     * @param username Lo username dell'utente per cui caricare la sessione.
     * @return L'oggetto {@link SessioneQuiz} caricato dal file, o {@code null} se il file non esiste,
     * se si verifica un errore I/O, o se la classe dell'oggetto non viene trovata.
     */
    public static SessioneQuiz loadSessione(String username) {
>>>>>>> Stashed changes
        String filename = "salvataggio_" + username + ".dat";
        SessioneQuiz sessioneQuiz = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            sessioneQuiz = (SessioneQuiz) ois.readObject();
        } catch (FileNotFoundException e) {
<<<<<<< Updated upstream
            throw new SessioneNonCaricataException("File di salvataggio non trovato: " + filename);
        } catch (IOException e) {
            throw new SessioneNonCaricataException("Errore di I/O durante il caricamento della sessione");
        } catch (ClassNotFoundException e) {
            throw new SessioneNonCaricataException("Classe SessioneQuiz non trovata durante il caricamento");
        } catch (Exception e) {
            throw new SessioneNonCaricataException("Errore generico durante il caricamento della sessione");
=======
            System.out.println("File di salvataggio non trovato per l'utente " + username + ": " + filename);
        } catch (IOException e) {
            System.err.println("Errore di I/O durante il caricamento della sessione per l'utente " + username + ":");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Classe SessioneQuiz non trovata durante il caricamento per l'utente " + username + ":");
            e.printStackTrace();
        } catch (Exception e) {
            // Cattura qualsiasi altra eccezione generica
            System.err.println("Errore generico durante il caricamento della sessione per l'utente " + username + ":");
            e.printStackTrace();
>>>>>>> Stashed changes
        }
        System.out.println("Ho trovato la sessione: " + sessioneQuiz); // Per debugging
        return sessioneQuiz;
    }

    /**
     * Elimina il file di salvataggio di una sessione associata a un determinato username.
     * Questo è utile quando una sessione è stata completata e non è più necessario mantenerne lo stato salvato.
     *
     * @param username Lo username dell'utente per cui eliminare il file di salvataggio.
     */
    public static void eliminaSessione(String username){
        String filename = "salvataggio_"+username+".dat";
        File file = new File(filename);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.err.println("Impossibile eliminare il file di salvataggio: " + filename);
            }
        } else {
            System.out.println("Nessun file di salvataggio da eliminare per l'utente " + username + ": " + filename + " non trovato.");
        }
    }
}