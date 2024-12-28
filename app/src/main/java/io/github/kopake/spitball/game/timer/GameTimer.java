package io.github.kopake.spitball.game.timer;

import static io.github.kopake.spitball.game.timer.TimerPhase.ONE;
import static io.github.kopake.spitball.game.timer.TimerPhase.THREE;
import static io.github.kopake.spitball.game.timer.TimerPhase.TWO;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;

import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.RoundCancelEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.TimerTickEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class GameTimer implements Listener {

    private static final int TIMER_1_FREQUENCY = 600;
    private static final int TIMER_2_FREQUENCY = TIMER_1_FREQUENCY / 2;
    private static final int TIMER_3_FREQUENCY = TIMER_2_FREQUENCY / 2;

    private CountDownTimer timer1;
    private CountDownTimer timer2;
    private CountDownTimer timer3;

    private void initializeTimers() {

        long totalTimeOfTimerInMilliseconds = getTotalTimeOfTimerInMilliSeconds();
        long timePerTimerInMilliseconds = totalTimeOfTimerInMilliseconds / 3;

        timer1 = new CountDownTimer(timePerTimerInMilliseconds, TIMER_1_FREQUENCY) {
            @Override
            public void onTick(long l) {
                EventManager.getInstance().dispatchEvent(new TimerTickEvent(ONE));
            }

            @Override
            public void onFinish() {
                timer2.start();
            }
        };

        timer2 = new CountDownTimer(timePerTimerInMilliseconds, TIMER_2_FREQUENCY) {
            @Override
            public void onTick(long l) {
                EventManager.getInstance().dispatchEvent(new TimerTickEvent(TWO));
            }

            @Override
            public void onFinish() {
                timer3.start();
            }
        };

        timer3 = new CountDownTimer(timePerTimerInMilliseconds, TIMER_3_FREQUENCY) {
            @Override
            public void onTick(long l) {
                EventManager.getInstance().dispatchEvent(new TimerTickEvent(THREE));
            }

            @Override
            public void onFinish() {
                EventManager.getInstance().dispatchEvent(new RoundEndEvent());
            }
        };
    }

    private long getTotalTimeOfTimerInMilliSeconds() {
        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        int meanRoundTime = sharedPreferences.getInt("round_length_average", 90);
        boolean shouldRandomizeRoundLength = sharedPreferences.getBoolean("round_length_randomized", true);
        int roundTimeRange = sharedPreferences.getInt("round_length_range", 15);

        double roundLengthInSeconds = meanRoundTime;
        if (shouldRandomizeRoundLength) {
            // 99.7% of values fall inside of 3 std devs
            roundLengthInSeconds += (new Random().nextGaussian() * roundTimeRange / 3.0);
        }

        // Robustness check to ensure that rounds are never too short
        if (roundLengthInSeconds < 5)
            roundLengthInSeconds = 5;

        return (long) roundLengthInSeconds * 1000;
    }

    @EventHandler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        new Handler(Looper.getMainLooper()).post(() -> {
            initializeTimers();
            timer1.start();
        });
    }

    @EventHandler
    public void onRoundCancel(RoundCancelEvent roundCancelEvent) {
        new Handler(Looper.getMainLooper()).post(() -> {
            timer1.cancel();
            timer2.cancel();
            timer3.cancel();
        });
    }
}
