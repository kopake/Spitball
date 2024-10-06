package io.github.kopake.catchphrase.ui.activity.gameinprogress;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.NextButtonPressEvent;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.RoundCancelEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

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
    }

    public void onNextButtonClick(View view) {
        EventManager.getInstance().dispatchEvent(new NextButtonPressEvent());
    }

    public void updateWordText(String word) {
        TextView wordTextView = findViewById(R.id.currentWordTextView);
        wordTextView.setText(word);
    }

    @Override
    public void onBackPressed() {
        EventManager.getInstance().dispatchEvent(new RoundCancelEvent());
    }
}