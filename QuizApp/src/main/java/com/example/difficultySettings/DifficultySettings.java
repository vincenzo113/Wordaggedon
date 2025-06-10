package com.example.difficultySettings;

/**
 * Questa classe fornisce impostazioni relative alla difficoltà per un quiz o un'applicazione simile.
 * Permette di ottenere limiti di tempo basati sul livello di difficoltà e il numero di testi.
 */
public class DifficultySettings {

    /**
     * Restituisce il limite di tempo (in secondi) per un dato livello di difficoltà.
     *
     * @param difficolta L'enumerazione {@link DifficultyEnum} che rappresenta il livello di difficoltà.
     * @return Il limite di tempo in secondi:
     * - 30 secondi per la difficoltà {@link DifficultyEnum#EASY}.
     * - 40 secondi per la difficoltà {@link DifficultyEnum#MEDIUM}.
     * - 60 secondi per la difficoltà {@link DifficultyEnum#HARD}.
     * - Di default, 30 secondi se la difficoltà non è riconosciuta.
     */
    public static int getTimeLimit(DifficultyEnum difficolta) {
        switch (difficolta) {
            case EASY:
                return 30;
            case MEDIUM:
                return 40;
            case HARD:
                return 60;
            default:
                // Valore di default nel caso in cui un nuovo livello di difficoltà non sia gestito.
                return 30;
        }
    }

    /**
     * Restituisce il numero predefinito di testi da utilizzare.
     * Attualmente, questo valore è fisso a 3.
     *
     * @return Il numero di testi, che è 3.
     */
    public static int getNumeroTesti(){
        return 3;
    }
}







