package io.github.kopake.catchphrase.game.event.listeners;

import android.util.Log;

import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.GameEndEvent;
import io.github.kopake.catchphrase.game.event.GameStartEvent;
import io.github.kopake.catchphrase.game.event.NextButtonPressEvent;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.PointAddEvent;
import io.github.kopake.catchphrase.game.event.RoundCancelEvent;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.TimerTickEvent;

public class LogListener implements Listener {

    @EventHandler
    public void onGameEndEvent(GameEndEvent event) {
        Log.i("Catchphrase", "Game Ended");
    }

    @EventHandler
    public void onGameStartEvent(GameStartEvent event) {
        Log.i("Catchphrase", "Game Started");
    }

    @EventHandler
    public void onNextWordEvent(NextWordEvent event) {
        Log.i("Catchphrase", "Next Word: " + event.getWord());
    }

    @EventHandler
    public void onPointAddEvent(PointAddEvent event) {
        Log.i("Catchphrase", "Point added to Team " + event.getTeam().name());
    }

    @EventHandler
    public void onRoundCancelEvent(RoundCancelEvent event) {
        Log.i("Catchphrase", "Round Cancelled");
    }

    @EventHandler
    public void onRoundEndEvent(RoundEndEvent event) {
        Log.i("Catchphrase", "Round Ended");
    }

    @EventHandler
    public void onRoundStartEvent(RoundStartEvent event) {
        Log.i("Catchphrase", "Round Started");
    }

    @EventHandler
    public void onTimerTickEvent(TimerTickEvent event) {
//        Log.i("Catchphrase", "Timer Tick: " + event.getTimerPhase());
    }

    @EventHandler
    public void onNextButtonPress(NextButtonPressEvent nextButtonPressEvent) {
        Log.i("Catchphrase", "Next Button Pressed");
    }
}
