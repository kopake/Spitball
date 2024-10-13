package io.github.kopake.catchphrase.ui;

import android.app.Activity;
import android.media.MediaPlayer;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.GameEndEvent;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.ScoreModifyEvent;
import io.github.kopake.catchphrase.game.event.TimerTickEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class SoundManager implements Listener {

    private Activity activity;

    public SoundManager(Activity activity) {
        this.activity = activity;
    }


    @EventHandler
    public void timerTickSound(TimerTickEvent event) {

        switch (event.getTimerPhase()) {
            case ONE:
                playSound(R.raw.tick_1);
                break;
            case TWO:
                playSound(R.raw.tick_2);
                break;
            case THREE:
                playSound(R.raw.tick_3);
                break;
        }
    }

    @EventHandler
    public void endOfRoundBuzzer(RoundEndEvent roundEndEvent) {
        playSound(R.raw.buzzer);
    }

    @EventHandler
    public void pointAddSound(ScoreModifyEvent pointAddEvent) {
        if (pointAddEvent.getValueChange() > 0) {
            playSound(R.raw.add_point);
        } else {
            //TODO make this play a different sound if the score add was negative
            playSound(R.raw.add_point);
        }
    }

    @EventHandler
    public void victorySound(GameEndEvent gameEndEvent) {
        playSound(R.raw.win_1);
    }

    private void playSound(int id) {
        Thread thread = new Thread(() -> {
            MediaPlayer mp = MediaPlayer.create(activity, id);
            mp.setLooping(false);
            mp.setOnCompletionListener(mplayer -> {
                mplayer.reset();
                mplayer.release();
            });
            mp.start();
        });
        thread.start();
    }
}
