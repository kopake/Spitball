package io.github.kopake.catchphrase.file;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordListParser {

    private static final String WORD_LISTS_LOCATION = "/Catchphrase/words";

    private static final FilenameFilter WORD_FILE_FILTER = (dir, name) -> name.toLowerCase().endsWith(".txt");

    public static List<String> getCategoryNames() {
        File wordDirectory = new File(WORD_LISTS_LOCATION);
        Log.i("Catchphrase", String.valueOf(wordDirectory.mkdirs()));
        

        Log.i("Catchphrase", wordDirectory.getAbsolutePath());

        try {
            return Arrays.stream(wordDirectory.list(WORD_FILE_FILTER))
                    .map(fileName -> fileName.substring(0, fileName.lastIndexOf('.')))
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    public static List<String> getListOfWordsInCategory(List<String> categories) {
        return categories.stream()
                .flatMap(category -> getListOfWordsInCategory(category).stream())
                .collect(Collectors.toList());
    }

    public static List<String> getListOfWordsInCategory(String categoryName) {
        return getWordsInTxtFile(getCategoryTxtFile(categoryName));
    }

    private static File getCategoryTxtFile(String categoryName) {
        String fullFilePath = WORD_LISTS_LOCATION + "/" + categoryName;
        if (fullFilePath.endsWith(".txt"))
            fullFilePath += ".txt";

        return new File(fullFilePath);
    }

    private static List<String> getWordsInTxtFile(File file) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
