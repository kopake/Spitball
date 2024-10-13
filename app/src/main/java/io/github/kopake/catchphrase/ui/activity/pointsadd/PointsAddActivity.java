package io.github.kopake.catchphrase.ui.activity.pointsadd;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.Scoreboard;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.ScoreModifyEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;
import io.github.kopake.catchphrase.game.team.Team;

public class PointsAddActivity extends AppCompatActivity {

    private static PointsAddActivity instance;

    public static PointsAddActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_add);
        hideNavigationBar();

        if (instance == null) {
            EventManager.getInstance().addListener(new Listener() {
                @EventHandler
                public void onPointAddEvent(ScoreModifyEvent pointAddEvent) {
                    displayScores();
                }
            });
            EventManager.getInstance().addListener(new Listener() {
                @EventHandler
                public void onRoundEndEvent(RoundEndEvent roundEndEvent) {
                    displayScores();
                }
            });
            instance = this;
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }


    public void onTeamOneAddButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new ScoreModifyEvent(Team.ONE, 1));
    }

    public void onTeamOneSubtractButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new ScoreModifyEvent(Team.ONE, -1));
    }

    public void onTeamTwoAddButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new ScoreModifyEvent(Team.TWO, 1));
    }

    public void onTeamTwoSubtractButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new ScoreModifyEvent(Team.TWO, -1));
    }

    public void onStartNextRoundButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new RoundStartEvent());
    }

    public void displayScores() {
        // Run a little later so that the scoreboard can update
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Scoreboard scoreboard = Scoreboard.getInstance();
            displayTeamOneScore(scoreboard.getTeamOneScore());
            displayTeamTwoScore(scoreboard.getTeamTwoScore());
        }, 100);
    }


    public void displayTeamOneScore(int n) {
        TextView teamOneScoreTextView = findViewById(R.id.teamOneScore);
        teamOneScoreTextView.setText(String.valueOf(n));
    }

    public void displayTeamTwoScore(int n) {
        TextView teamTwoScoreTextView = findViewById(R.id.teamTwoScore);
        teamTwoScoreTextView.setText(String.valueOf(n));
    }

    @Override
    public void onBackPressed() {
        //Ignore back presses on this screen
    }

}