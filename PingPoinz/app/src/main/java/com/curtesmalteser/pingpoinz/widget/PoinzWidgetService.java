package com.curtesmalteser.pingpoinz.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;

import com.curtesmalteser.pingpoinz.data.db.EventDbModel;
import com.curtesmalteser.pingpoinz.data.provider.TaskPoinzContentProvider;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class PoinzWidgetService extends IntentService {

    public static final String ACTION_GET_EVENTS = "com.curtesmalteser.pingpoinz.widget.get_events";

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

    public static void startActionGetEvents(Context context) {
        Intent intent = new Intent(context, PoinzWidgetService.class);
        intent.setAction(ACTION_GET_EVENTS);
        context.startService(intent);
    }

    private void handleGetEvents() {

        Cursor taskCursor = getContentResolver().query(
                TaskPoinzContentProvider.URI_EVENTS,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Sort order to return in Cursor */
                null);

        String title = "N/A";
        if (taskCursor != null && taskCursor.getCount() > 0) {
            taskCursor.moveToFirst();

            int index = taskCursor.getColumnIndex("title");
            title = taskCursor.getString(index);
            taskCursor.close();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, PoinzWidgetProvider.class));
        //Now update all widgets
        //PoinzWidgetProvider.updateEventsWidget(this, appWidgetManager, imgRes, appWidgetIds);
        PoinzWidgetProvider.updateEventsWidgets(this, appWidgetManager, "que nervos", appWidgetIds);

    }


}
