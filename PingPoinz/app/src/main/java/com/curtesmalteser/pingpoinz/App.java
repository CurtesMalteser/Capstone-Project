package com.curtesmalteser.pingpoinz;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by António "Curtes Malteser" Bastião on 28/07/2018.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

    }
}
