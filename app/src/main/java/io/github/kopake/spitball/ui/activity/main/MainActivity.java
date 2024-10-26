package io.github.kopake.spitball.ui.activity.main;

import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.GameStartEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.listeners.LogListener;
import io.github.kopake.spitball.file.FileSystemUtilities;
import io.github.kopake.spitball.file.WordListParser;
import io.github.kopake.spitball.game.NextWordChooser;
import io.github.kopake.spitball.game.Scoreboard;
import io.github.kopake.spitball.game.model.WordList;
import io.github.kopake.spitball.game.timer.GameTimer;
import io.github.kopake.spitball.ui.SoundManager;
import io.github.kopake.spitball.ui.VibrationManager;
import io.github.kopake.spitball.ui.activity.ActivityManager;
import io.github.kopake.spitball.ui.activity.gameinprogress.GameInProgressActivity;
import io.github.kopake.spitball.ui.activity.main.checkbox.CheckboxAdapter;
import io.github.kopake.spitball.ui.activity.main.checkbox.CheckboxItem;
import io.github.kopake.spitball.ui.activity.pointsadd.PointsAddActivity;

public class MainActivity extends AppCompatActivity {

    private GameInProgressActivity gameInProgressActivity = new GameInProgressActivity();

    private PointsAddActivity pointsAddActivity = new PointsAddActivity();

    private RecyclerView recyclerView;
    private CheckboxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideNavigationBar();
        createCheckboxList();
        registerListeners();


//        setContentView(R.layout.activity_main);

        Log.i("Spitball", FileSystemUtilities.getSpitballRootDirectory().getAbsolutePath());
    }


    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    public void onStartButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (adapter.getCheckedItems().isEmpty()) {
            Toast.makeText(this, "Select one or more categories to begin.", Toast.LENGTH_SHORT).show();
            return;
        }

        EventManager eventManager = EventManager.getInstance();
        eventManager.dispatchEvent(new GameStartEvent(adapter.getCheckedItems()));
        eventManager.dispatchEvent(new RoundStartEvent());
    }

    private void createCheckboxList() {
        recyclerView = findViewById(R.id.recyclerView);

        List<CheckboxItem<WordList>> wordListCheckBoxItems = WordListParser.getAllWordLists().stream()
                .map(wordList -> new CheckboxItem<>(wordList, false))
                .collect(Collectors.toList());

        adapter = new CheckboxAdapter(this, wordListCheckBoxItems);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void registerListeners() {
        EventManager eventManager = EventManager.getInstance();

        //Register main functionality listeners
        eventManager.addListener(ActivityManager.getInstance());
        eventManager.addListener(NextWordChooser.getInstance());
        eventManager.addListener(new GameTimer());
        eventManager.addListener(Scoreboard.getInstance());
        eventManager.addListener(new LogListener());

        //Register UI listeners
        eventManager.addListener(new SoundManager(this));
        eventManager.addListener(new VibrationManager(this));
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}