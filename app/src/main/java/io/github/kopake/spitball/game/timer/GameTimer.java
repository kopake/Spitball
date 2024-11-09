package io.github.kopake.spitball.game.timer;

import static io.github.kopake.spitball.game.timer.TimerPhase.ONE;
import static io.github.kopake.spitball.game.timer.TimerPhase.THREE;
import static io.github.kopake.spitball.game.timer.TimerPhase.TWO;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.RoundCancelEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.TimerTickEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class GameTimer implements Listener {

    private static final double ROUND_MEAN_LENGTH_IN_SECONDS = 90;
    private static final double ROUND_STD_DEV_IN_SECONDS = 5;

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
        int meanRoundTime = Spitball.getContext().getSharedPreferences("SpitballSettings", Context.MODE_PRIVATE).getInt("averageRoundTime", 90);
        //TODO get this math working
        return (long) (/*new Random().nextGaussian() * ROUND_STD_DEV_IN_SECONDS +*/ meanRoundTime) * 1000;
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
