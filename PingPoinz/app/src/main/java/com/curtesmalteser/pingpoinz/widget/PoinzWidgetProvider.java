package com.curtesmalteser.pingpoinz.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.db.EventDbModel;
import com.curtesmalteser.pingpoinz.data.db.PoinzDao;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;

/**
 * Implementation of App Widget functionality.
 */
public class PoinzWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                EventDbModel eventDbModel,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poinz_widget_provider);

        views.setTextViewText(R.id.appwidget_text, eventDbModel.getTitle());

        views.setTextViewText(R.id.appwidget_date, eventDbModel.getStartTime());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateEventsWidgets(Context context, AppWidgetManager appWidgetManager, EventDbModel eventDbModel, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, eventDbModel, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        PoinzWidgetSync.scheduleFirebaseJobDispatcher(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        PoinzWidgetSync.scheduleFirebaseJobDispatcher(context);

        PoinzDao dao = PoinzDatabase.getDatabase(context).poinzDao();
        dao.getAllEvents().observeForever(eventDbModels -> {
                    for (int appWidgetId : appWidgetIds) {

                        if (eventDbModels != null)
                            updateAppWidget(context, appWidgetManager, eventDbModels.get(0), appWidgetId);
                    }
                }
        );
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        if(appWidgetIds == null || appWidgetIds.length == 0) {
            PoinzWidgetSync.cancelDispatcher();
        }
        super.onDeleted(context, appWidgetIds);
    }
}

