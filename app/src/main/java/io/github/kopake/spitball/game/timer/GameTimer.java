package io.github.kopake.spitball.game.timer;

import static io.github.kopake.spitball.game.timer.TimerPhase.ONE;
import static io.github.kopake.spitball.game.timer.TimerPhase.THREE;
import static io.github.kopake.spitball.game.timer.TimerPhase.TWO;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import io.github.kopake.spitball.game.event.EventHandler;
import io.github.kopake.spitball.game.event.EventManager;
import io.github.kopake.spitball.game.event.RoundCancelEvent;
import io.github.kopake.spitball.game.event.RoundEndEvent;
import io.github.kopake.spitball.game.event.RoundStartEvent;
import io.github.kopake.spitball.game.event.TimerTickEvent;
import io.github.kopake.spitball.game.event.listeners.Listener;

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

    private static long getTotalTimeOfTimerInMilliSeconds() {
        // TODO uncomment this when time length doesn't need to be short for testing
        // return (long) (new Random().nextGaussian() * ROUND_STD_DEV_IN_SECONDS + ROUND_MEAN_LENGTH_IN_SECONDS);
        return 10000;
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
