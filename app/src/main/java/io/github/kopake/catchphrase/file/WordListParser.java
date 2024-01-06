package io.github.kopake.catchphrase.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.kopake.catchphrase.game.model.WordList;

public class WordListParser {

    private static final String WORD_LIST_FILE_EXTENSION = ".txt";

    private static List<WordList> wordLists;


    public static List<WordList> getAllWordLists() {
        if (wordLists == null)
            parseWordLists();

        return wordLists;
    }

    private static void parseWordLists() {
        wordLists = new ArrayList<>();
        File wordListDirectory = FileSystemUtilities.getWordListsDirectory();
        if (!wordListDirectory.isDirectory())
            throw new RuntimeException("Error while parsing word lists directory");

        FilenameFilter textFileFilter = (dir, name) -> name.toLowerCase().endsWith(WORD_LIST_FILE_EXTENSION);
        for (File txtFile : Objects.requireNonNull(wordListDirectory.listFiles(textFileFilter))) {
            wordLists.add(new WordList(txtFile));
        }
    }

    public static WordList getWordListByName(String wordListName) {
        for (WordList wordList : getAllWordLists()) {
            if (wordList.getName().equals(wordListName))
                return wordList;
        }
        return null;
    }

    public static List<String> getWordListNames() {
        List<String> wordListNames = new ArrayList<>();
        for (WordList wordList : getAllWordLists()) {
            wordListNames.add(wordList.getName());
        }
        return wordListNames;
    }
}
