package com.mumudev.timemanager.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Benio on 2015/7/27.
 */
public class AppContext extends Application {
    private static AppContext sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /**
     *
     * @return application instance
     */
    public static synchronized AppContext getInstance() {
        return sInstance;
    }

    /**
     *
     * @return applicationContext
     */
    public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }
}
