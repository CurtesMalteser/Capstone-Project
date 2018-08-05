package com.curtesmalteser.pingpoinz.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.curtesmalteser.pingpoinz.data.db.PoinzDao;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class PoinzWidgetService extends JobService {

    private Context mContext;

    @Override
    public boolean onStartJob(JobParameters job) {
        mContext = getApplicationContext();
        handleGetEvents();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private void handleGetEvents() {
        PoinzDao dao = PoinzDatabase.getDatabase(mContext).poinzDao();
        dao.getAllCurrencies().observeForever(eventDbModels -> {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, PoinzWidgetProvider.class));
                    PoinzWidgetProvider.updateEventsWidgets(this, appWidgetManager, eventDbModels.get(0), appWidgetIds);
                }
        );
    }
}
