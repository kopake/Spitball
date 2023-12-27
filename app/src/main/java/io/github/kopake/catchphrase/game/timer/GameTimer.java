package io.github.kopake.catchphrase.game.timer;

import static io.github.kopake.catchphrase.game.timer.TimerPhase.ONE;
import static io.github.kopake.catchphrase.game.timer.TimerPhase.THREE;
import static io.github.kopake.catchphrase.game.timer.TimerPhase.TWO;

import android.os.CountDownTimer;

import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.RoundCancelEvent;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.TimerTickEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class GameTimer implements Listener {

    private static final int TIMER_1_FREQUENCY = 600;
    private static final int TIMER_2_FREQUENCY = TIMER_1_FREQUENCY / 2;
    private static final int TIMER_3_FREQUENCY = TIMER_2_FREQUENCY / 2;

    private CountDownTimer timer1;
    private CountDownTimer timer2;
    private CountDownTimer timer3;

    private void initializeTimers() {

        long totalTimeOfTimerInMilliseconds = getTotalTimeOfTimerInMilliSeconds();
        long timerPerTimerInMilliseconds = totalTimeOfTimerInMilliseconds / 3;

        timer1 = new CountDownTimer(timerPerTimerInMilliseconds, TIMER_1_FREQUENCY) {
            @Override
            public void onTick(long l) {
                EventManager.getInstance().dispatchEvent(new TimerTickEvent(ONE));
            }

            @Override
            public void onFinish() {
                timer2.start();
            }
        };

        timer2 = new CountDownTimer(timerPerTimerInMilliseconds, TIMER_2_FREQUENCY) {
            @Override
            public void onTick(long l) {
                EventManager.getInstance().dispatchEvent(new TimerTickEvent(TWO));
            }

            @Override
            public void onFinish() {
                timer3.start();
            }
        };

        timer3 = new CountDownTimer(timerPerTimerInMilliseconds, TIMER_3_FREQUENCY) {
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
        return 3000;
    }

    @EventHandler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        initializeTimers();
        timer1.start();
    }

    @EventHandler
    public void onRoundCancel(RoundCancelEvent roundCancelEvent) {
        timer1.cancel();
        timer2.cancel();
        timer3.cancel();
    }


}
