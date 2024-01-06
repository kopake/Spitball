package io.github.kopake.catchphrase.ui;

import android.app.Activity;
import android.media.MediaPlayer;

import io.github.kopake.catchphrase.R;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.GameEndEvent;
import io.github.kopake.catchphrase.game.event.PointAddEvent;
import io.github.kopake.catchphrase.game.event.RoundEndEvent;
import io.github.kopake.catchphrase.game.event.TimerTickEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class SoundManager implements Listener {

    private Activity activity;

    private MediaPlayer mediaPlayer;

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
    public void pointAddSound(PointAddEvent pointAddEvent) {
        playSound(R.raw.add_point);
    }

    @EventHandler
    public void victorySound(GameEndEvent gameEndEvent) {
        playSound(R.raw.win_1);
    }

    private void playSound(int id) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                MediaPlayer mp = MediaPlayer.create(activity, id);
                mp.setLooping(false);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mplayer) {
                        mplayer.reset();
                        mplayer.release();
                    }
                });
                mp.start();

            }

        };
        thread.start();
    }
}
