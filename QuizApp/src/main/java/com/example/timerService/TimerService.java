package com.example.timerService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 * TimerService è un servizio JavaFX che esegue un timer di conto alla rovescia per una durata specificata.
 * <p>
 * Il timer aggiorna il proprio progresso ogni secondo ed esegue un Runnable fornito al termine.
 * </p>
 */
public class TimerService extends Service<Void> {

    private int durationInSeconds;
    private Runnable onFinish;

    private Timeline timeline;
    private int currentSecond;

    /**
     * Constructs a TimerService with the specified duration and a callback to be executed upon completion.
     *
     * @param durationInSeconds the total duration of the timer in seconds
     * @param onFinish the Runnable to execute when the timer finishes
     */
    public TimerService(int durationInSeconds,  Runnable onFinish) {
        this.durationInSeconds = durationInSeconds;
        this.onFinish = onFinish;
    }

    /**
     * Creates the background task that runs the timer logic.
     *
     * @return a Task that manages the timer countdown and triggers the onFinish callback
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                currentSecond = 0;

                timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    currentSecond++;
                    updateProgress(currentSecond,durationInSeconds);

                    if (currentSecond >= durationInSeconds) {
                        timeline.stop();
                        Platform.runLater(onFinish);
                    }


                }));

                timeline.setCycleCount(durationInSeconds);
                timeline.play();

                return null;
            }
        };
    }


}
