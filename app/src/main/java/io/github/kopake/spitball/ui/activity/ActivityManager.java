package io.github.kopake.spitball.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.GameEndEvent;
import io.github.kopake.spitball.event.RoundCancelEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.listeners.Listener;
import io.github.kopake.spitball.game.Scoreboard;
import io.github.kopake.spitball.ui.activity.gameinprogress.GameInProgressActivity;
import io.github.kopake.spitball.ui.activity.main.MainActivity;
import io.github.kopake.spitball.ui.activity.pointsadd.PointsAddActivity;
import io.github.kopake.spitball.ui.activity.winscreen.WinScreenActivity;

public class ActivityManager implements Listener {

    private static ActivityManager instance;

    private final Class<? extends Activity> homeScreen = MainActivity.class;
    private final Class<? extends Activity> gameInProgressScreen = GameInProgressActivity.class;
    private final Class<? extends Activity> pointsAddScreen = PointsAddActivity.class;
    private final Class<? extends Activity> winScreen = WinScreenActivity.class;


    private ActivityManager() {
    }

    public static synchronized ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    @EventHandler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        //Switch to game in progress screen
        openActivity(gameInProgressScreen);
    }

    @EventHandler
    public void onRoundEnd(RoundEndEvent roundEndEvent) {
        //Switch to points adding screen
        openActivity(pointsAddScreen);
    }

    @EventHandler
    public void onRoundCancel(RoundCancelEvent roundCancelEvent) {
        Scoreboard scoreboard = Scoreboard.getInstance();
        if (scoreboard.getLeftTeamScore() == 0 && scoreboard.getRightTeamScore() == 0) {
            openActivity(homeScreen);
        } else {
            openActivity(pointsAddScreen);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent gameEndEvent) {
        //Show victory screen for a short bit then go back to the home screen
        Bundle bundle = new Bundle();
        bundle.putSerializable(WinScreenActivity.WINNING_TEAM_BUNDLE_KEY, gameEndEvent.getWinningTeam());
        openActivity(winScreen, bundle);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            openActivity(homeScreen);
        }, WinScreenActivity.WIN_SCREEN_LENGTH_IN_MILLISECONDS);

    }

    private void openActivity(Class<? extends Activity> activityClass) {
        openActivity(activityClass, null);
    }

    private void openActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(() -> {
            Context context = Spitball.getContext();
            Intent intent = new Intent(context, activityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            if (bundle != null)
                intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }


}
