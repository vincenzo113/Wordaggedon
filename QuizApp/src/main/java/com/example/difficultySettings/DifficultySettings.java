package com.example.difficultySettings;

public class DifficultySettings {

    /**
     * Restituisce il limite di tempo in secondi associato al livello di difficoltà specificato.
     *
     * @param difficolta Il livello di difficoltà selezionato.
     * @return Il tempo limite in secondi. Valori: EASY = 30, MEDIUM = 20, HARD = 10.
     */
    public static int getTimeLimit(DifficultyEnum difficolta) {
        switch (difficolta) {
            case EASY:
                return 10;
            case MEDIUM:
                return 20;
            case HARD:
                return 30;
            default:
                return 30; // valore predefinito
        }
    }

}
