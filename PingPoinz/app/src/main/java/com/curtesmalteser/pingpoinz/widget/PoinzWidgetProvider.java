package com.curtesmalteser.pingpoinz.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.PoinzDetailsActivity;
import com.curtesmalteser.pingpoinz.data.db.EventDbModel;
import com.curtesmalteser.pingpoinz.data.db.PoinzDao;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;

/**
 * Implementation of App Widget functionality.
 */
public class PoinzWidgetProvider extends AppWidgetProvider {

    private PoinzWidgetService poinzWidgetService;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                EventDbModel eventDbModel,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poinz_widget_provider);

        Intent i = new Intent(context, PoinzDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        views.setTextViewText(R.id.appwidget_text, eventDbModel.getTitle());
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        views.setTextViewText(R.id.appwidget_date, eventDbModel.getStartTime());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateEventsWidgets(Context context, AppWidgetManager appWidgetManager, EventDbModel eventDbModel, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, eventDbModel, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        if (poinzWidgetService == null) {
            poinzWidgetService = new PoinzWidgetService();
            poinzWidgetService.startActionGetEvents(context);
        }
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        PoinzDao dao = PoinzDatabase.getDatabase(context).poinzDao();
        dao.getAllCurrencies().observeForever(eventDbModels -> {
                    for (int appWidgetId : appWidgetIds) {

                        updateAppWidget(context, appWidgetManager, eventDbModels.get(0), appWidgetId);
                    }
                }
        );

        if (poinzWidgetService == null) {
            poinzWidgetService = new PoinzWidgetService();
            poinzWidgetService.startActionGetEvents(context);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

