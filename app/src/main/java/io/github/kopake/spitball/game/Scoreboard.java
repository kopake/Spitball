package io.github.kopake.spitball.game;

import android.content.SharedPreferences;

import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.GameEndEvent;
import io.github.kopake.spitball.event.GameStartEvent;
import io.github.kopake.spitball.event.ScoreModifyEvent;
import io.github.kopake.spitball.event.listeners.Listener;
import io.github.kopake.spitball.game.team.Team;

public class Scoreboard implements Listener {

    private static Scoreboard instance = new Scoreboard();
    private int leftTeamScore = 0;
    private int rightTeamScore = 0;

    private Scoreboard() {
    }

    public static Scoreboard getInstance() {
        return instance;
    }

    public int getLeftTeamScore() {
        return leftTeamScore;
    }

    public int getRightTeamScore() {
        return rightTeamScore;
    }

    public int getScore(Team team) {
        switch (team) {
            case LEFT:
                return getLeftTeamScore();
            case RIGHT:
                return getRightTeamScore();
        }
        return -1;
    }

    @EventHandler
    public void onGameStart(GameStartEvent gameStartEvent) {
        leftTeamScore = 0;
        rightTeamScore = 0;
    }

    @EventHandler
    public void onScoreModify(ScoreModifyEvent scoreModifyEvent) {
        switch (scoreModifyEvent.getTeam()) {
            case LEFT:
                leftTeamScore = leftTeamScore + scoreModifyEvent.getValueChange();
                break;
            case RIGHT:
                rightTeamScore = rightTeamScore + scoreModifyEvent.getValueChange();
                break;
        }

        if (leftTeamScore < 0)
            leftTeamScore = 0;
        if (rightTeamScore < 0)
            rightTeamScore = 0;

        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        int pointsNeededToWin = sharedPreferences.getInt("points_to_win", 7);
        boolean winByTwo = sharedPreferences.getBoolean("points_win_by_two", true);

        if (teamHasWon(leftTeamScore, rightTeamScore, pointsNeededToWin, winByTwo)) {
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.LEFT));
        } else if (teamHasWon(rightTeamScore, leftTeamScore, pointsNeededToWin, winByTwo)) {
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.RIGHT));
        }
    }

    private static boolean teamHasWon(int teamScore, int opponentScore, int pointsToWin, boolean isWinningByTwoRequired) {
        if (teamScore >= pointsToWin) {
            if (isWinningByTwoRequired) {
                return (teamScore - opponentScore) >= 2;
            } else {
                return true;
            }
        }
        return false;
    }
}
