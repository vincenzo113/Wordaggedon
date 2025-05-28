package com.example.TimerService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;
import java.util.function.BiConsumer;

public class TimerService extends Service<Void> {

    private int durationInSeconds;
    private Runnable onFinish;

    private Timeline timeline;
    private int currentSecond;

    public TimerService(int durationInSeconds,  Runnable onFinish) {
        this.durationInSeconds = durationInSeconds;
        this.onFinish = onFinish;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
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

    public void stopTimer() {
        timeline.stop();
        cancel();
    }
}
