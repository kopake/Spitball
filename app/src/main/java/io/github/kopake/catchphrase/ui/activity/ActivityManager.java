package io.github.kopake.catchphrase.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import io.github.kopake.catchphrase.Catchphrase;
import io.github.kopake.catchphrase.game.Scoreboard;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.GameEndEvent;
import io.github.kopake.catchphrase.game.event.RoundCancelEvent;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;
import io.github.kopake.catchphrase.ui.activity.gameinprogress.GameInProgressActivity;
import io.github.kopake.catchphrase.ui.activity.main.MainActivity;
import io.github.kopake.catchphrase.ui.activity.pointsadd.PointsAddActivity;

public class ActivityManager implements Listener {

    private static ActivityManager instance;

    private final Class<? extends Activity> homeScreen = MainActivity.class;
    private final Class<? extends Activity> gameInProgressScreen = GameInProgressActivity.class;
    private final Class<? extends Activity> pointsAddScreen = PointsAddActivity.class;


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
        if (scoreboard.getTeamOneScore() == 0 && scoreboard.getTeamTwoScore() == 0) {
            openActivity(homeScreen);
        } else {
            openActivity(pointsAddScreen);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent gameEndEvent) {
        //Show victory screen for a short bit then go back to the home screen
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(Catchphrase.getContext(), "Winner: " + gameEndEvent.getWinningTeam(), Toast.LENGTH_SHORT).show();
        });
        openActivity(homeScreen);
    }

    private void openActivity(Class<? extends Activity> activityClass) {
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(() -> {
            Context context = Catchphrase.getContext();
            Intent intent = new Intent(context, activityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(intent);
        });
    }


}
