package io.github.kopake.catchphrase.game;

import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class CatchphraseGame implements Listener {

    private String categoryName;

    public CatchphraseGame(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void start() {

    }


}
