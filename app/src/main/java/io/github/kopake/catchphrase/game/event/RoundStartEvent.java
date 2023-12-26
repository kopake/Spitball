package io.github.kopake.catchphrase.game.event;

public class RoundStartEvent implements Event {

    private int timerDuration;

    public RoundStartEvent() {
        //TODO get timer duration here
    }

    public int getTimerDuration() {
        return timerDuration;
    }
}
