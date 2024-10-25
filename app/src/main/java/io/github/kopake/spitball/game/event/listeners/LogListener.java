package io.github.kopake.spitball.game.event.listeners;

import android.util.Log;

import io.github.kopake.spitball.game.event.EventHandler;
import io.github.kopake.spitball.game.event.GameEndEvent;
import io.github.kopake.spitball.game.event.GameStartEvent;
import io.github.kopake.spitball.game.event.NextButtonPressEvent;
import io.github.kopake.spitball.game.event.NextWordEvent;
import io.github.kopake.spitball.game.event.RoundCancelEvent;
import io.github.kopake.spitball.game.event.RoundEndEvent;
import io.github.kopake.spitball.game.event.RoundStartEvent;
import io.github.kopake.spitball.game.event.ScoreModifyEvent;
import io.github.kopake.spitball.game.event.TimerTickEvent;

public class LogListener implements Listener {

    @EventHandler
    public void onGameEndEvent(GameEndEvent event) {
        Log.i("Spitball", "Game Ended");
    }

    @EventHandler
    public void onGameStartEvent(GameStartEvent event) {
        String outputString = "Game Started";
        if (event.getWordLists() != null && !event.getWordLists().isEmpty())
            outputString += " with word lists: " + event.getWordLists().toString();
        Log.i("Spitball", outputString);
    }

    @EventHandler
    public void onNextWordEvent(NextWordEvent event) {
        Log.i("Spitball", "Next Word: " + event.getWord());
    }

    @EventHandler
    public void onPointAddEvent(ScoreModifyEvent event) {
        Log.i("Spitball", String.format("Score for team: %s modified (added %d)", event.getTeam().name(), event.getValueChange()));
    }

    @EventHandler
    public void onRoundCancelEvent(RoundCancelEvent event) {
        Log.i("Spitball", "Round Cancelled");
    }

    @EventHandler
    public void onRoundEndEvent(RoundEndEvent event) {
        Log.i("Spitball", "Round Ended");
    }

    @EventHandler
    public void onRoundStartEvent(RoundStartEvent event) {
        Log.i("Spitball", "Round Started");
    }

    @EventHandler
    public void onTimerTickEvent(TimerTickEvent event) {
//        Log.i("Spitball", "Timer Tick: " + event.getTimerPhase());
    }

    @EventHandler
    public void onNextButtonPress(NextButtonPressEvent nextButtonPressEvent) {
        Log.i("Spitball", "Next Button Pressed");
    }
}
