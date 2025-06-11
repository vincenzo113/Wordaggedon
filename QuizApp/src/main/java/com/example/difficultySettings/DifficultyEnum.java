package com.example.difficultySettings;

import java.io.Serializable;

/**
 * Enum che rappresenta i livelli di difficoltà disponibili nell'applicazione.
 * I livelli influenzano vari parametri del gioco, come il tempo limite e il numero di testi mostrati.
 * Valori disponibili:
 * <ul>
 *   <li>{@code EASY} - Livello facile</li>
 *   <li>{@code MEDIUM} - Livello medio</li>
 *   <li>{@code HARD} - Livello difficile</li>
 * </ul>
 */
public enum DifficultyEnum implements Serializable {
    EASY, MEDIUM ,HARD;
}
