package io.github.kopake.catchphrase.file;

import android.content.ContextWrapper;

import java.io.File;

import io.github.kopake.catchphrase.Catchphrase;

public class FileSystemUtilities {

    private static final String EXTERNAL_FILES_DIRECTORY = null;
    public static final String ROOT_CATCHPHRASE_FOLDER_NAME = "Catchphrase";
    public static final String CATEGORIES_FOLDER_NAME = "word_lists";
    public static final String RESOURCE_PACKS_FOLDER_NAME = "resource_packs";

    public static File getCatchphraseRootDirectory() {
        ContextWrapper contextWrapper = new ContextWrapper(Catchphrase.getContext());
        File externalFilesDirectory = contextWrapper.getExternalFilesDir(EXTERNAL_FILES_DIRECTORY);
        File rootCatchphraseDirectory = new File(externalFilesDirectory, ROOT_CATCHPHRASE_FOLDER_NAME);

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
