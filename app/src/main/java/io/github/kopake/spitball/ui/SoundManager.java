package io.github.kopake.spitball.ui;

import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.event.EventHandler;
import io.github.kopake.spitball.event.GameEndEvent;
import io.github.kopake.spitball.event.NextButtonPressEvent;
import io.github.kopake.spitball.event.RoundEndEvent;
import io.github.kopake.spitball.event.RoundStartEvent;
import io.github.kopake.spitball.event.ScoreModifyEvent;
import io.github.kopake.spitball.event.TimerTickEvent;
import io.github.kopake.spitball.event.WordListSelectEvent;
import io.github.kopake.spitball.event.listeners.Listener;

public class SoundManager implements Listener {

    private Context context;

    private SoundPool soundPool;

    private static final int SOUND_POOL_MAX_STREAMS = 4;

    private Map<Integer, Integer> soundIDMap;

    private static final int[] SOUNDS_TO_LOAD = {
            R.raw.tick_1,
            R.raw.tock_1,
            R.raw.tick_2,
            R.raw.tock_2,
            R.raw.tick_3,
            R.raw.tock_3,
            R.raw.add_point,
            R.raw.minus_point,
            R.raw.buzzer,
            R.raw.win_1,
            R.raw.next_button_click,
            R.raw.change_category
    };

    /**
     * False -> next timer sound should be a tick sound
     * True  -> next timer sound should be a tock sound
     */
    private boolean nTickOrTock = false;

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
                if (!nTickOrTock)
                    playSoundFromSoundPool(R.raw.tick_1);
                else
                    playSoundFromSoundPool(R.raw.tock_1);
                break;
            case TWO:
                if (!nTickOrTock)
                    playSoundFromSoundPool(R.raw.tick_2);
                else
                    playSoundFromSoundPool(R.raw.tock_2);
                break;
            case THREE:
                if (!nTickOrTock)
                    playSoundFromSoundPool(R.raw.tick_3);
                else
                    playSoundFromSoundPool(R.raw.tock_3);
                break;
        }

        nTickOrTock = !nTickOrTock;
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
    public void nextButtonClickSound(NextButtonPressEvent nextButtonPressEvent) {
        playSoundFromSoundPool(R.raw.next_button_click);
    }

    @EventHandler
    public void roundStartButtonClickSound(RoundStartEvent roundStartEvent) {
        playSoundFromSoundPool(R.raw.next_button_click);
    }

    @EventHandler
    public void categorySelectedClickSound(WordListSelectEvent wordListSelectEvent) {
        playSoundFromSoundPool(R.raw.change_category);
    }

    @EventHandler
    public void victorySound(GameEndEvent gameEndEvent) {
        playSoundFromSoundPool(R.raw.win_1);
    }

    private void playSoundFromSoundPool(int id) {
        Integer mappedID = soundIDMap.get(id);
        if (mappedID == null) {
            Log.e("Spitball", "Sound with id: %d was not loaded. Update SOUNDS_TO_LOAD to play the sound");
            return;
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            soundPool.play(soundIDMap.get(id), 1, 1, 0, 0, 1);
        });
    }
}
