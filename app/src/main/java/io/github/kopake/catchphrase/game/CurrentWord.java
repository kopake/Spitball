package io.github.kopake.catchphrase.game;

import java.util.List;

import io.github.kopake.catchphrase.game.event.CategoryChangeEvent;
import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.NextWordEvent;
import io.github.kopake.catchphrase.game.event.RoundStartEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;

public class CurrentWord implements Listener {

    private String currentCategoryString;

    private String currentWord;

    private List<String> wordList;

    private int currentWordIndex = 0;


    public String getCurrentWord() {
        return currentWord;
    }

    @EventHandler
    public void onCategoryChange(CategoryChangeEvent categoryChangeEvent) {
//        currentCategoryString = categoryChangeEvent.getCategoryName();
//        currentWord = null;
        //TODO parse category word file to wordList
    }

    @EventHandler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        cycleToNextWord();
    }

    public void onNextWordButtonPress(NextWordEvent nextWordEvent) {
        cycleToNextWord();
    }

    private void cycleToNextWord() {
        currentWordIndex++;
    }

}
