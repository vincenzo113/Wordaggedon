package com.example.difficultySettings;

public class DifficultySettings {

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
