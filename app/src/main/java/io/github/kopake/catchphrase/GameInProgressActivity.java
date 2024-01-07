package io.github.kopake.catchphrase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.NextButtonPressEvent;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class GameInProgressActivity extends AppCompatActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_in_progress);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(view -> EventManager.getInstance().dispatchEvent(new NextButtonPressEvent()));
    }

    @EventHandler
    public void onNextWordEvent(NextWordEvent nextWordEvent) {
        TextView wordTextView = findViewById(R.id.currentWordTextView);
        wordTextView.setText(nextWordEvent.getWord());
    }
}