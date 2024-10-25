package io.github.kopake.spitball;

import android.app.Application;
import android.content.Context;

import io.github.kopake.spitball.game.event.EventManager;

public class Spitball extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onTerminate() {
        EventManager.getInstance().shutdown();
        super.onTerminate();
    }
}
