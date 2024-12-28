package io.github.kopake.spitball.ui.activity.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.Spitball;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        init();
    }

    private void init() {
        updateTextPreference("team_name_left");
        updateTextPreference("team_name_right");
        initResetAllButton();
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

    private void initResetAllButton() {
        Preference resetAllPreference = findPreference("reset_to_default");
        if (resetAllPreference != null) {
            resetAllPreference.setOnPreferenceClickListener(preference -> {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Reset to default")
                        .setMessage("Select 'OK' to reset all settings to their default value?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> resetAllSettings())
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            });
        }
    }

    private void resetAllSettings() {
        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        PreferenceManager.setDefaultValues(Spitball.getContext(), R.xml.preferences, true);

        //refresh the display
        setPreferenceScreen(null);
        addPreferencesFromResource(R.xml.preferences);
        init();
    }
}
