package com.example.timerService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 * {@code TimerService} è un servizio JavaFX che fornisce un conto alla rovescia basato su un intervallo di tempo specificato.
 * Estende {@link Service} per integrarsi nel framework di concorrenza di JavaFX, consentendo operazioni in background
 * e aggiornamenti dell'interfaccia utente sicuri.
 *
 * <p>Questo servizio può essere utilizzato per implementare timer in applicazioni JavaFX,
 * ad esempio per limiti di tempo in quiz o giochi, eseguendo una {@link Runnable} quando il timer scade.</p>
 */
public class TimerService extends Service<Void> {

    private int durationInSeconds;
    private Runnable onFinish;

    private Timeline timeline;
    private int currentSecond;

    /**
     * Costruisce una nuova istanza di {@code TimerService}.
     *
     * @param durationInSeconds La durata totale del timer in secondi.
     * @param onFinish Un oggetto {@link Runnable} che verrà eseguito sul JavaFX Application Thread
     * una volta che il timer ha raggiunto la sua durata.
     */
    public TimerService(int durationInSeconds, Runnable onFinish) {
        this.durationInSeconds = durationInSeconds;
        this.onFinish = onFinish;
    }

    /**
     * Crea e restituisce il {@link Task} che definisce l'operazione del timer.
     * Questo task gestisce il conto alla rovescia e l'esecuzione del callback {@code onFinish}.
     *
     * @return Un'istanza di {@link Task}<{@link Void}> che esegue il timer.
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                currentSecond = 0; // Inizializza il contatore dei secondi.

                // Crea una Timeline per gestire gli intervalli di un secondo.
                timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    currentSecond++; // Incrementa il contatore ogni secondo.
                    // Aggiorna il progresso del task, utile per le UI bindate al progresso.
                    updateProgress(currentSecond, durationInSeconds);

                    // Se il tempo corrente raggiunge la durata totale, ferma il timer ed esegui il callback.
                    if (currentSecond >= durationInSeconds) {
                        timeline.stop(); // Ferma la Timeline.
                        // Esegui il Runnable onFinish sul JavaFX Application Thread.
                        Platform.runLater(onFinish);
                    }
                }));

                // Imposta il numero di cicli della Timeline uguale alla durata in secondi,
                // assicurando che il timer si esegua per l'intera durata.
                timeline.setCycleCount(durationInSeconds);
                timeline.play(); // Avvia il timer.

                return null; // Il task non restituisce un valore.
            }
        };
    }

    /**
     * Interrompe il timer se è attualmente in esecuzione.
     * Questo metodo può essere chiamato per terminare il conto alla rovescia prematuramente.
     */
    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
