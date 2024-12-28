package io.github.kopake.spitball.ui.activity.settings;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.kopake.spitball.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        updateTextPreference("team_name_left");
        updateTextPreference("team_name_right");
    }

    private void updateTextPreference(String key) {
        // Find the EditTextPreference
        EditTextPreference textPreference = findPreference(key);
        if (textPreference != null) {
            // Set initial value when the fragment is displayed
            updatePreferenceSummary(textPreference);


            // Listen for changes and update dynamically
            textPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                updatePreferenceSummary((EditTextPreference) preference, (String) newValue);
                return true; // Save the new value
            });
        }
    }

    private void updatePreferenceSummary(EditTextPreference preference) {

        if (preference != null) {
            String value = preference.getText();
            preference.setSummaryProvider(pref -> value == null || value.isEmpty() ? "Not set" : value);
        }
    }

    private void updatePreferenceSummary(EditTextPreference preference, String newValue) {
        if (preference != null) {
            preference.setSummaryProvider(pref -> newValue.isEmpty() ? "Not set" : newValue);
        }
    }
}
