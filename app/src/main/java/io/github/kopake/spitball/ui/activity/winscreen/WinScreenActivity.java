package io.github.kopake.spitball.ui.activity.winscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.game.team.Team;

public class WinScreenActivity extends AppCompatActivity {

    private static final String WINNING_MESSAGE_FORMAT = "%s\nWins!";
    public static final String WINNING_TEAM_BUNDLE_KEY = "Winner";
    public static final int WIN_SCREEN_LENGTH_IN_MILLISECONDS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        hideNavigationBar();
        handleIntentForWinnerInformation(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntentForWinnerInformation(intent);
    }

    private void handleIntentForWinnerInformation(Intent intent) {
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        Team winningTeam = (Team) bundle.getSerializable(WINNING_TEAM_BUNDLE_KEY);
        displayWinner(winningTeam);
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    public void displayWinner(Team team) {
        runOnUiThread(() -> {
            TextView winningMessageTextView = findViewById(R.id.winnerMessageTextView);
            SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
            String teamName;
            if (team == Team.LEFT)
                teamName = sharedPreferences.getString("team_name_left", "Team One");
            else
                teamName = sharedPreferences.getString("team_name_right", "Team Two");

            winningMessageTextView.setText(String.format(WINNING_MESSAGE_FORMAT, teamName));
        });
    }
}
