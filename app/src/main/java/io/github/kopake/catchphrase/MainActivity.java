package io.github.kopake.catchphrase;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.github.kopake.catchphrase.game.CurrentWord;
import io.github.kopake.catchphrase.game.Scoreboard;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.GameStartEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.listeners.LogListener;
import io.github.kopake.catchphrase.game.timer.GameTimer;
import io.github.kopake.catchphrase.ui.SoundManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Catchprhase", "Start");


        EventManager eventManager = EventManager.getInstance();
        eventManager.addListener(new CurrentWord());
        eventManager.addListener(new GameTimer());
        eventManager.addListener(new Scoreboard());
        eventManager.addListener(new LogListener());

        eventManager.addListener(new SoundManager(this));

//        eventManager.dispatchEvent(new CategoryChangeEvent("hi"));
        eventManager.dispatchEvent(new GameStartEvent());
        eventManager.dispatchEvent(new RoundStartEvent());

    }

}