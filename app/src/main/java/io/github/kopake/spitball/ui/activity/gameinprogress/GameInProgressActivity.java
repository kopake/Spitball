package io.github.kopake.spitball.ui.activity.gameinprogress;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.NextWordEvent;
import io.github.kopake.spitball.event.RoundCancelEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class GameInProgressActivity extends AppCompatActivity {

    private static GameInProgressActivity instance;

    public static GameInProgressActivity getInstance() {
        return instance;
    }

    // The first time this activity created, it was because a RoundStartEvent was dispatched, so
    // this variable should be true to start
    private boolean roundInProgress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_in_progress);
        hideNavigationBar();

        if (instance == null) {
            EventManager.getInstance().addListener(new Listener() {
                @EventHandler
                public void onNextWordEvent(NextWordEvent nextWordEvent) {
                    runOnUiThread(() -> {
                        updateWordText(nextWordEvent.getWord());
                    });
                }
            });
            EventManager.getInstance().addListener(new Listener() {
                @EventHandler
                public void onRoundStart(RoundStartEvent roundStartEvent) {
                    roundInProgress = true;
                }
            });
            EventManager.getInstance().addListener(new Listener() {
                @EventHandler
                public void onRoundEnd(RoundEndEvent roundEndEvent) {
                    roundInProgress = false;
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

    public void updateWordText(String word) {
        TextView wordTextView = findViewById(R.id.currentWordTextView);
        wordTextView.setText(word.toUpperCase());
    }

    @Override
    public void onBackPressed() {
        abortRound();
    }

    @Override
    protected void onPause() {
        abortRound();
        super.onPause();
    }

    private void abortRound() {
        if (roundInProgress) {
            EventManager.getInstance().dispatchEvent(new RoundCancelEvent());
        }
    }
}