package com.curtesmalteser.pingpoinz.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.curtesmalteser.pingpoinz.data.db.PoinzDao;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class PoinzWidgetService extends IntentService {

    public static final String ACTION_GET_EVENTS = "com.curtesmalteser.pingpoinz.widget.get_events";
    private Context mContext;

    public PoinzWidgetService() {
        super("PoinzWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_EVENTS.equals(action)) {
                handleGetEvents();
            }
        }
    }

    public void startActionGetEvents(Context context) {
        Intent intent = new Intent(context, PoinzWidgetService.class);
        intent.setAction(ACTION_GET_EVENTS);
        context.startService(intent);
        mContext = context;
    }

    private void handleGetEvents() {
        PoinzDao dao = PoinzDatabase.getDatabase(mContext).poinzDao();
        dao.getAllCurrencies().observeForever(eventDbModels -> {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, PoinzWidgetProvider.class));
                    //Now update all widgets
                    //PoinzWidgetProvider.updateEventsWidget(this, appWidgetManager, imgRes, appWidgetIds);
                    PoinzWidgetProvider.updateEventsWidgets(this, appWidgetManager, eventDbModels.get(0).getTitle(), appWidgetIds);
                }
        );
    }
}
