package io.github.kopake.spitball.game;

import io.github.kopake.spitball.game.event.listeners.Listener;

public class SpitballGame implements Listener {

    private String categoryName;

    public SpitballGame(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void start() {

    }


}
