package io.github.kopake.spitball;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import io.github.kopake.spitball.event.EventManager;

public class Spitball extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public void onTerminate() {
        EventManager.getInstance().shutdown();
        super.onTerminate();
    }
}
