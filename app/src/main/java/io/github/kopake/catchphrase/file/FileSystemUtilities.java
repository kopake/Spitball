package io.github.kopake.catchphrase.file;

import android.content.ContextWrapper;

import java.io.File;

import io.github.kopake.catchphrase.Catchphrase;

public class FileSystemUtilities {

    public static final String CATEGORIES_FOLDER_NAME = "word_lists";
    public static final String RESOURCE_PACKS_FOLDER_NAME = "resource_packs";

    public static File getCatchphraseRootDirectory() {
        ContextWrapper contextWrapper = new ContextWrapper(Catchphrase.getContext());
        File rootCatchphraseDirectory = contextWrapper.getExternalFilesDir(null);
        return getDirectoryCreateIfItDoesntExist(rootCatchphraseDirectory);
    }

    public static File getWordListsDirectory() {
        return getDirectoryCreateIfItDoesntExist(new File(getCatchphraseRootDirectory(), CATEGORIES_FOLDER_NAME));
    }

    public static File getResourcePacksDirectory() {
        return getDirectoryCreateIfItDoesntExist(new File(getCatchphraseRootDirectory(), RESOURCE_PACKS_FOLDER_NAME));
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
        throw new RuntimeException("Could not access root Catchphrase directory: " + directory.getAbsolutePath());
    }
}
