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
    private int teamOneScore = 0;
    private int teamTwoScore = 0;

    private Scoreboard() {
    }

    public static Scoreboard getInstance() {
        return instance;
    }

    public int getTeamOneScore() {
        return teamOneScore;
    }

    public int getTeamTwoScore() {
        return teamTwoScore;
    }

    public int getScore(Team team) {
        switch (team) {
            case ONE:
                return getTeamOneScore();
            case TWO:
                return getTeamTwoScore();
        }
        return -1;
    }

    @EventHandler
    public void onGameStart(GameStartEvent gameStartEvent) {
        teamOneScore = 0;
        teamTwoScore = 0;
    }

    @EventHandler
    public void onScoreModify(ScoreModifyEvent scoreModifyEvent) {
        switch (scoreModifyEvent.getTeam()) {
            case ONE:
                teamOneScore = teamOneScore + scoreModifyEvent.getValueChange();
                break;
            case TWO:
                teamTwoScore = teamTwoScore + scoreModifyEvent.getValueChange();
                break;
        }

        if (teamOneScore < 0)
            teamOneScore = 0;
        if (teamTwoScore < 0)
            teamTwoScore = 0;

        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        int pointsNeededToWin = sharedPreferences.getInt("points_to_win", 7);
        boolean winByTwo = sharedPreferences.getBoolean("points_win_by_two", true);

        if (teamHasWon(teamOneScore, teamTwoScore, pointsNeededToWin, winByTwo)) {
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.ONE));
        } else if (teamHasWon(teamTwoScore, teamOneScore, pointsNeededToWin, winByTwo)) {
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.TWO));
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
