package io.github.kopake.spitball.file;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.game.model.WordList;

public class WordListParser {

    private static final String ASSETS_WORD_LISTS_DIRECTORY = "word_lists";
    private static final String WORD_LIST_FILE_EXTENSION = ".txt";

    private static List<WordList> wordLists;


    public static List<WordList> getAllWordLists() {
        if (wordLists == null)
            parseWordLists();

        return wordLists;
    }

    private static void parseWordLists() {
        wordLists = new ArrayList<>();

        wordLists.addAll(getWordListsFromAssets());
        wordLists.addAll(getWordListsFromCustomWordListsDirectory());
    }

    private static List<WordList> getWordListsFromAssets() {
        List<WordList> assetWordLists = new ArrayList<>();

        AssetManager assetManager = Spitball.getContext().getAssets();

        try {
            String[] wordListFileNames = assetManager.list(ASSETS_WORD_LISTS_DIRECTORY);

            if (wordListFileNames == null) {
                return assetWordLists;
            }

            for (String wordListFileName : wordListFileNames) {
                String fullWordListFilePath = String.format("%s%s%s", ASSETS_WORD_LISTS_DIRECTORY, File.separator, wordListFileName);
                assetWordLists.add(new WordList(cleanUpWordListFileName(wordListFileName), assetManager.open(fullWordListFilePath)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return assetWordLists;
    }

    private static List<WordList> getWordListsFromCustomWordListsDirectory() {
        List<WordList> customWordLists = new ArrayList<>();

        File wordListDirectory = FileSystemUtilities.getCustomWordListsDirectory();
        if (!wordListDirectory.isDirectory())
            throw new RuntimeException("Error while parsing word lists directory");

        FilenameFilter textFileFilter = (dir, name) -> name.toLowerCase().endsWith(WORD_LIST_FILE_EXTENSION);
        for (File txtFile : Objects.requireNonNull(wordListDirectory.listFiles(textFileFilter))) {
            try {
                customWordLists.add(new WordList(cleanUpWordListFileName(txtFile.getName()), new FileInputStream(txtFile)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return customWordLists;
    }

    private static String cleanUpWordListFileName(String name) {
        return name.replace(".txt", "").replace("_", " ");
    }

}
