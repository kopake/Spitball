package io.github.kopake.catchphrase;

import android.app.Application;
import android.content.Context;

public class Catchphrase extends Application {

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


}
