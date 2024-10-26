package io.github.kopake.spitball.event;

public class NextWordEvent implements Event {

    private String word;

    public NextWordEvent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
