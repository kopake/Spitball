package io.github.kopake.catchphrase.game.event;

import io.github.kopake.catchphrase.game.timer.TimerPhase;

public class TimerTickEvent implements Event {
    private TimerPhase timerPhase;

    public TimerTickEvent(TimerPhase timerPhase) {
        this.timerPhase = timerPhase;
    }

    public TimerPhase getTimerPhase() {
        return timerPhase;
    }
}
