package io.github.kopake.spitball.event;

public class NextWordEvent implements Event {

    private final String word;

    public NextWordEvent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
