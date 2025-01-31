package io.github.kopake.spitball.file;

import android.content.ContextWrapper;

import java.io.File;

import io.github.kopake.spitball.Spitball;

public class FileSystemUtilities {

    public static final String CATEGORIES_FOLDER_NAME = "word_lists";

    public static File getSpitballRootDirectory() {
        ContextWrapper contextWrapper = new ContextWrapper(Spitball.getContext());
        File rootSpitballDirectory = contextWrapper.getExternalFilesDir(null);
        return getDirectoryCreateIfItDoesntExist(rootSpitballDirectory);
    }

    public static File getCustomWordListsDirectory() {
        return getDirectoryCreateIfItDoesntExist(new File(getSpitballRootDirectory(), CATEGORIES_FOLDER_NAME));
    }


    private static File getDirectoryCreateIfItDoesntExist(File directory) {

        if (directory == null)
            return null;

        if (directory.exists()) {
            return directory;
        }

        try {
            if (directory.mkdirs()) {
                return directory;
            }
        } catch (Exception e) {

        }
        throw new RuntimeException("Could not access root Spitball directory: " + directory.getAbsolutePath());
    }
}
