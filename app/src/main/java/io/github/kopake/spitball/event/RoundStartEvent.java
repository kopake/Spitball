package io.github.kopake.spitball.event;

public class RoundStartEvent implements Event {

    private int timerDuration;

    public RoundStartEvent() {
        //TODO get timer duration here
    }

    public int getTimerDuration() {
        return timerDuration;
    }
}
