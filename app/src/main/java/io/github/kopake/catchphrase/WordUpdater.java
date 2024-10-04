package io.github.kopake.catchphrase;

import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class WordUpdater implements Listener {

    @EventHandler
    public void onNextWordEvent(NextWordEvent nextWordEvent) {

        GameInProgressActivity gameInProgressActivity = GameInProgressActivity.getInstance();
        gameInProgressActivity.updateWordText(nextWordEvent.getWord());


    }
}
