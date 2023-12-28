package io.github.kopake.catchphrase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.kopake.catchphrase.file.WordListParser;
import io.github.kopake.catchphrase.game.CurrentWord;
import io.github.kopake.catchphrase.game.Scoreboard;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.GameStartEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.listeners.LogListener;
import io.github.kopake.catchphrase.game.timer.GameTimer;
import io.github.kopake.catchphrase.ui.CheckboxAdapter;
import io.github.kopake.catchphrase.ui.SoundManager;
import io.github.kopake.catchphrase.ui.VibrationManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hideNavigationBar();
        initButtonClick();
        createCheckboxList(WordListParser.getCategoryNames());
        registerListeners();


    }


    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initButtonClick() {
        Button button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.i("Catchphrase", WordListParser.getListOfWordsInCategory());

                EventManager eventManager = EventManager.getInstance();
                eventManager.dispatchEvent(new GameStartEvent());
                eventManager.dispatchEvent(new RoundStartEvent());
            }
        });
    }

    private void createCheckboxList(List<String> categoryNames) {
        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set the adapter
        CheckboxAdapter adapter = new CheckboxAdapter(this, categoryNames);
        recyclerView.setAdapter(adapter);
    }


    private void registerListeners() {
        EventManager eventManager = EventManager.getInstance();

        //Register main functionality listeners
        eventManager.addListener(new CurrentWord());
        eventManager.addListener(new GameTimer());
        eventManager.addListener(new Scoreboard());
        eventManager.addListener(new LogListener());

        //Register UI listeners
        eventManager.addListener(new SoundManager(this));
        eventManager.addListener(new VibrationManager(this));


    }

}