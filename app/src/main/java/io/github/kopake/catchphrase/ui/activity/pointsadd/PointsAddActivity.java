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
import io.github.kopake.catchphrase.game.NextWordChooser;
import io.github.kopake.catchphrase.game.Scoreboard;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.ScoreModifyEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;
import io.github.kopake.catchphrase.game.team.Team;

public class PointsAddActivity extends AppCompatActivity {

    //The word last word received from next word events (used to display the final word of the round on this activity)
    private String mostRecentNextWord = "";

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

    public void updateDisplay() {
        // Run a little later so that the scoreboard can update
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Scoreboard scoreboard = Scoreboard.getInstance();
            displayTeamOneScore(scoreboard.getTeamOneScore());
            displayTeamTwoScore(scoreboard.getTeamTwoScore());
            displayMostRecentNextWord(NextWordChooser.getInstance().getMostRecentWord());
        }, 100);
    }


    private void displayTeamOneScore(int n) {
        TextView teamOneScoreTextView = findViewById(R.id.teamOneScore);
        teamOneScoreTextView.setText(String.valueOf(n));
    }

    private void displayTeamTwoScore(int n) {
        TextView teamTwoScoreTextView = findViewById(R.id.teamTwoScore);
        teamTwoScoreTextView.setText(String.valueOf(n));
    }

    private void displayMostRecentNextWord(String mostRecentNextWord) {
        TextView mostRecentNextWordTextView = findViewById(R.id.mostRecentNextWord);
        mostRecentNextWordTextView.setText(mostRecentNextWord);
    }

    @Override
    public void onBackPressed() {
        //Ignore back presses on this screen
    }

}