package io.github.kopake.spitball.event;

import io.github.kopake.spitball.game.timer.TimerPhase;

public class TimerTickEvent implements Event {
    private final TimerPhase timerPhase;

    public TimerTickEvent(TimerPhase timerPhase) {
        this.timerPhase = timerPhase;
    }

    public TimerPhase getTimerPhase() {
        return timerPhase;
    }
}
