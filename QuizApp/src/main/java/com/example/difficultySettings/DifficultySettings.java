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
                return 30;
            case MEDIUM:
                return 20;
            case HARD:
                return 10;
            default:
                return 30; // valore predefinito
        }
    }
    /**
     * Restituisce il numero di testi da visualizzare in base al livello di difficoltà.
     *
     * @param difficolta Il livello di difficoltà selezionato.
     * @return Il numero di testi da mostrare. Valori: EASY = 1, MEDIUM = 2, HARD = 3.
     */
    public static int getNumeroTesti(DifficultyEnum difficolta){
        switch (difficolta) {
            case EASY:
                return 1;
            case MEDIUM:
                return 2;
            case HARD:
                return 3;
            default:
                return 3;
        }
    }






}
