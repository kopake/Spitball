package io.github.kopake.catchphrase.game.event;

import java.util.Collection;

import io.github.kopake.catchphrase.game.model.WordList;

public class GameStartEvent implements Event {
    private Collection<WordList> wordLists;

    public GameStartEvent(Collection<WordList> wordLists) {
        this.wordLists = wordLists;
    }

    public Collection<WordList> getWordLists() {
        return wordLists;
    }
}
