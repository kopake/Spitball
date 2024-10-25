package io.github.kopake.spitball.game.event;

public class NextWordEvent implements Event {

    private String word;

    public NextWordEvent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
