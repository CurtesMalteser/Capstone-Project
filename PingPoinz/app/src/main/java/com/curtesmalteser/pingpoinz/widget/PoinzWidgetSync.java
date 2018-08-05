package com.curtesmalteser.pingpoinz.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by António "Curtes Malteser" Bastião on 05/08/2018.
 */
public class PoinzWidgetSync {

    private static final int SYNC_INTERVAL_HOURS = 3;

    //private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    //private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    private static final String CURRENCIES_SYNC_TAG = "currencies_sync";

    private static FirebaseJobDispatcher mDispatcher;

    public static void scheduleFirebaseJobDispatcher(@NonNull final Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        mDispatcher = new FirebaseJobDispatcher(driver);

        Job syncCurrenciesJob = mDispatcher.newJobBuilder()
                .setService(PoinzWidgetService.class)
                .setTag(CURRENCIES_SYNC_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(
                        Trigger.executionWindow(
                                 SYNC_INTERVAL_SECONDS,
                                SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                        )
                ).setReplaceCurrent(true)
                .build();

        mDispatcher.schedule(syncCurrenciesJob);

        sInitialized = true;
    }

    public static void cancelDispatcher(){
        mDispatcher.cancel(CURRENCIES_SYNC_TAG);
    }
}