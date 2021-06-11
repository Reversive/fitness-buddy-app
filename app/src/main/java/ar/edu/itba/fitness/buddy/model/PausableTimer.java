package ar.edu.itba.fitness.buddy.model;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Executable;
import java.util.function.Function;

public class PausableTimer {
    private static final int interval = 1000;

    private final TextView toUpdate;
    private final Runnable callback;

    private String timeStr;
    private long remainingTime;
    private CountDownTimer timer;

    public PausableTimer(long time, TextView toUpdate, Runnable callback) {
        this.toUpdate = toUpdate;
        this.remainingTime = time;
        this.callback = callback;
        timeStr = Long.toString(remainingTime);
        toUpdate.setText(timeStr);
        createTimer(time);
    }

    public void pause() {
        timer.cancel();
    }

    public void play() {
        createTimer(remainingTime);
    }

    public void finish() {
        timer.cancel();
    }

    private void createTimer(long time) {
        timer = new CountDownTimer(time * interval, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = Math.floorDiv(millisUntilFinished, interval);
                timeStr = Long.toString(remainingTime);
                toUpdate.setText(timeStr);
            }

            @Override
            public void onFinish() {
                timeStr = "0";
                toUpdate.setText(timeStr);
                callback.run();
            }
        };
        timer.start();
    }
}
