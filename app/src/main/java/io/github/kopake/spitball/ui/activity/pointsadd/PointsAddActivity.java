package io.github.kopake.spitball.ui.activity.pointsadd;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.ScoreModifyEvent;
import io.github.kopake.spitball.event.listeners.Listener;
import io.github.kopake.spitball.game.NextWordChooser;
import io.github.kopake.spitball.game.Scoreboard;
import io.github.kopake.spitball.game.team.Team;

public class PointsAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_add);
        hideNavigationBar();
        EventManager.getInstance().addListener(new Listener() {
            @EventHandler
            public void onScoreModifyEvent(ScoreModifyEvent scoreModifyEvent) {
                updateDisplay();
            }

            @EventHandler
            public void onRoundEvent(RoundEndEvent roundEndEvent) {
                updateDisplay();
            }
        });
        updateDisplay();
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }


    public void onLeftTeamAddButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        modifyScore(Team.LEFT, 1);
    }

    public void onLeftTeamSubtractButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        modifyScore(Team.LEFT, -1);
    }

    public void onRightTeamAddButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        modifyScore(Team.RIGHT, 1);
    }

    public void onRightTeamSubtractButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        modifyScore(Team.RIGHT, -1);
    }


    private void modifyScore(Team team, int value) {
        EventManager.getInstance().dispatchEvent(new ScoreModifyEvent(team, value));
    }


    public void onStartNextRoundButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        EventManager.getInstance().dispatchEvent(new RoundStartEvent());
    }

    public void updateDisplay() {
        // Run a little later so that the scoreboard can update
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Scoreboard scoreboard = Scoreboard.getInstance();
            displayLeftTeamScore(scoreboard.getLeftTeamScore());
            displayRightTeamScore(scoreboard.getRightTeamScore());
            displayMostRecentNextWord(NextWordChooser.getInstance().getMostRecentWord().toUpperCase());
            displayTeamNames();
        }, 100);
    }


    private void displayLeftTeamScore(int n) {
        TextView leftTeamScoreTextView = findViewById(R.id.leftTeamScore);
        leftTeamScoreTextView.setText(String.valueOf(n));
    }

    private void displayRightTeamScore(int n) {
        TextView rightTeamScoreTextView = findViewById(R.id.rightTeamScore);
        rightTeamScoreTextView.setText(String.valueOf(n));
    }

    private void displayMostRecentNextWord(String mostRecentNextWord) {
        TextView mostRecentNextWordTextView = findViewById(R.id.mostRecentNextWord);
        mostRecentNextWordTextView.setText(mostRecentNextWord);
    }

    private void displayTeamNames() {
        TextView leftTeamNameTextView = findViewById(R.id.leftTeamHeader);
        TextView rightTeamNameTextView = findViewById(R.id.rightTeamHeader);

        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        String leftTeamName = sharedPreferences.getString("team_name_left", "Team One");
        String rightTeamName = sharedPreferences.getString("team_name_right", "Team Two");
        leftTeamNameTextView.setText(leftTeamName.toUpperCase());
        rightTeamNameTextView.setText(rightTeamName.toUpperCase());
    }


    @Override
    public void onBackPressed() {
        //Ignore back presses on this screen
    }

}