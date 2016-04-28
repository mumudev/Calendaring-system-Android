package com.mumudev.timemanager.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.ext.tools.HttpTools;

/**
 * Created by Benio on 2015/7/27.
 */
public class AppContext extends Application {
    private static AppContext sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        HttpTools.init(this);
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
