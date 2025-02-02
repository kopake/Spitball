package io.github.kopake.spitball.event;

import java.util.Collection;

import io.github.kopake.spitball.game.model.WordList;

public class GameStartEvent implements Event {
    private final Collection<WordList> wordLists;

    public GameStartEvent(Collection<WordList> wordLists) {
        this.wordLists = wordLists;
    }

    public Collection<WordList> getWordLists() {
        return wordLists;
    }
}
