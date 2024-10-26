package io.github.kopake.spitball.ui.activity.gameinprogress;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.NextButtonPressEvent;
import io.github.kopake.spitball.event.NextWordEvent;
import io.github.kopake.spitball.event.RoundCancelEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class GameInProgressActivity extends AppCompatActivity {

    private static GameInProgressActivity instance;

    public static GameInProgressActivity getInstance() {
        return instance;
    }

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

    public void onNextButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        EventManager.getInstance().dispatchEvent(new NextButtonPressEvent());
    }

    public void updateWordText(String word) {
        TextView wordTextView = findViewById(R.id.currentWordTextView);
        wordTextView.setText(word.toUpperCase());
    }

    @Override
    public void onBackPressed() {
        EventManager.getInstance().dispatchEvent(new RoundCancelEvent());
    }
}