package io.github.kopake.spitball.settings;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.kopake.spitball.Spitball;

public class SpitballSettings {

    private enum SettingParameter {
        LEFT_TEAM_NAME("leftTeamName", "Team One"),
        RIGHT_TEAM_NAME("rightTeamName", "Team Two"),
        AVERAGE_ROUND_TIME("averageRoundTime", 90),
        POINTS_NEEDED_TO_WIN("pointsNeededToWin", 7);

        private String key;

        private Object defaultValue;

        SettingParameter(String key, Object defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }
    }

    private static final String SPITBALL_SHARED_PREFERENCES_KEY = "SpitballSettings";

    private static final SharedPreferences SHARED_PREFERENCES = Spitball.getContext().getSharedPreferences(SPITBALL_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

    private static final SharedPreferences.Editor EDITOR = SHARED_PREFERENCES.edit();

    private static String getStringSafe(SettingParameter settingParameter) {
        try {
            String value = SHARED_PREFERENCES.getString(settingParameter.getKey(), settingParameter.getDefaultValue().toString());
            if (value == null || value.isEmpty()) {
                return settingParameter.getDefaultValue().toString();
            }
            return value;
        } catch (ClassCastException e) {
            return settingParameter.getDefaultValue().toString();
        }
    }

    private static int getIntSafe(SettingParameter settingParameter) {
        try {
            int value = SHARED_PREFERENCES.getInt(settingParameter.getKey(), (Integer) settingParameter.getDefaultValue());
            if (value <= 0) {
                return (int) settingParameter.getDefaultValue();
            }
            return value;
        } catch (ClassCastException e) {
            return (int) settingParameter.getDefaultValue();
        }
    }

    private static void putStringSafe(SettingParameter settingParameter, String string) {
        String stringToPut = string;
        if (string == null || string.isEmpty())
            stringToPut = settingParameter.getDefaultValue().toString();
        EDITOR.putString(settingParameter.getKey(), stringToPut);
        EDITOR.apply();
    }

    private static void putIntSafe(SettingParameter settingParameter, String intString) {
        int intToPut;
        try {
            intToPut = Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            intToPut = (int) settingParameter.getDefaultValue();
        }
        EDITOR.putInt(settingParameter.getKey(), intToPut);
        EDITOR.apply();
    }

    public static String getLeftTeamName() {
        return getStringSafe(SettingParameter.LEFT_TEAM_NAME);
    }

    public static void putLeftTeamName(String leftTeamName) {
        putStringSafe(SettingParameter.LEFT_TEAM_NAME, leftTeamName);
    }

    public static String getRightTeamName() {
        return getStringSafe(SettingParameter.RIGHT_TEAM_NAME);
    }

    public static void putRightTeamName(String rightTeamName) {
        putStringSafe(SettingParameter.RIGHT_TEAM_NAME, rightTeamName);
    }

    public static int getAverageRoundTime() {
        return getIntSafe(SettingParameter.AVERAGE_ROUND_TIME);
    }

    public static void putAverageRoundTime(String averageRoundTimeString) {
        putIntSafe(SettingParameter.AVERAGE_ROUND_TIME, averageRoundTimeString);
    }

    public static int getPointsNeededToWin() {
        return getIntSafe(SettingParameter.POINTS_NEEDED_TO_WIN);
    }

    public static void putPointsNeededToWin(String pointsNeededToWin) {
        putIntSafe(SettingParameter.POINTS_NEEDED_TO_WIN, pointsNeededToWin);
    }
}


