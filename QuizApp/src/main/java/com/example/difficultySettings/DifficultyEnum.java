package com.example.difficultySettings;

import java.io.Serializable;

/**
 * Enumerazione che rappresenta i diversi livelli di difficoltà disponibili per i quiz.
 * Questa enumerazione è serializzabile, il che significa che i suoi valori possono essere
 * convertiti in un flusso di byte e successivamente ricostruiti, utile per la persistenza
 * o la trasmissione su rete.
 */
public enum DifficultyEnum implements Serializable {
    /**
     * Rappresenta il livello di difficoltà Facile.
     */
    EASY,
    /**
     * Rappresenta il livello di difficoltà Medio.
     */
    MEDIUM,
    /**
     * Rappresenta il livello di difficoltà Difficile.
     */
    HARD;
}