package com.curtesmalteser.pingpoinz.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.widget.RemoteViews;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.PoinzDetailsActivity;
import com.curtesmalteser.pingpoinz.data.provider.TaskPoinzContentProvider;

/**
 * Implementation of App Widget functionality.
 */
public class PoinzWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String title,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poinz_widget_provider);

        Intent i = new Intent(context, PoinzDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        views.setTextViewText(R.id.appwidget_text, title);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateEventsWidgets(Context context, AppWidgetManager appWidgetManager, String title, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, title, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        PoinzWidgetService.startActionGetEvents(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        /*new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Cursor taskCursor = context.getContentResolver().query(
                        TaskPoinzContentProvider.URI_EVENTS,
                        *//* Columns; leaving this null returns every column in the table *//*
                        null,
                        *//* Optional specification for columns in the "where" clause above *//*
                        null,
                        *//* Values for "where" clause *//*
                        null,
                        *//* Sort order to return in Cursor *//*
                        null);

                String title = "N/A";
                if (taskCursor != null && taskCursor.getCount() > 0) {
                    taskCursor.moveToFirst();

                    int index = taskCursor.getColumnIndex("title");
                    title = taskCursor.getString(index);
                    taskCursor.close();
                }
                return title;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, s, appWidgetId);
                }
            }
        }.execute();
*/
        PoinzWidgetService.startActionGetEvents(context);

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

