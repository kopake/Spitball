package io.github.kopake.spitball.ui;

import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.GameEndEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.ScoreModifyEvent;
import io.github.kopake.spitball.event.TimerTickEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class SoundManager implements Listener {

    private Context context;

    private SoundPool soundPool;

    private static final int SOUND_POOL_MAX_STREAMS = 4;

    private Map<Integer, Integer> soundIDMap;

    private static final int[] SOUNDS_TO_LOAD = {
            R.raw.tick_1,
            R.raw.tick_2,
            R.raw.tick_3,
            R.raw.add_point,
            R.raw.minus_point,
            R.raw.buzzer,
            R.raw.win_1
    };

    public SoundManager(Context context) {
        this.context = context;
        this.soundPool = new SoundPool.Builder().setMaxStreams(SOUND_POOL_MAX_STREAMS).build();
        loadSounds();
    }

    private void loadSounds() {
        soundIDMap = new HashMap<>();
        for (int rawSoundID : SOUNDS_TO_LOAD) {
            int soundPoolID = soundPool.load(context, rawSoundID, 1);
            soundIDMap.put(rawSoundID, soundPoolID);
        }
    }

    @EventHandler
    public void timerTickSound(TimerTickEvent event) {

        switch (event.getTimerPhase()) {
            case ONE:
                playSoundFromSoundPool(R.raw.tick_1);
                break;
            case TWO:
                playSoundFromSoundPool(R.raw.tick_2);
                break;
            case THREE:
                playSoundFromSoundPool(R.raw.tick_3);
                break;
        }
    }

    @EventHandler
    public void endOfRoundBuzzer(RoundEndEvent roundEndEvent) {
        playSoundFromSoundPool(R.raw.buzzer);
    }

    @EventHandler
    public void pointAddSound(ScoreModifyEvent pointAddEvent) {
        if (pointAddEvent.getValueChange() > 0) {
            playSoundFromSoundPool(R.raw.add_point);
        } else {
            playSoundFromSoundPool(R.raw.minus_point);
        }
    }

    @EventHandler
    public void victorySound(GameEndEvent gameEndEvent) {
        playSoundFromSoundPool(R.raw.win_1);
    }

    private void playSoundFromSoundPool(int id) {

        new Handler(Looper.getMainLooper()).post(() -> {
            soundPool.play(soundIDMap.get(id), 1, 1, 0, 0, 1);
        });
    }
}
