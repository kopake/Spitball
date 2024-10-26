package io.github.kopake.spitball.ui;

import android.app.Activity;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class VibrationManager implements Listener {

    private Activity activity;

    public VibrationManager(Activity activity) {
        this.activity = activity;
    }

    @EventHandler
    public void buzzerVibration(RoundEndEvent roundEndEvent) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    // Short delay so that vibration lines up with buzzer sound
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                }
                Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }).start();

    }
}
