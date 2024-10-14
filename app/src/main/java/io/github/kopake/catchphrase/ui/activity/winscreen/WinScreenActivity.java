package io.github.kopake.catchphrase.ui.activity.winscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.team.Team;

public class WinScreenActivity extends AppCompatActivity {

    private static final String WINNING_MESSAGE_FORMAT = "Team %s Wins!";
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
            winningMessageTextView.setText(String.format(WINNING_MESSAGE_FORMAT, team.name()));
        });
    }
}
