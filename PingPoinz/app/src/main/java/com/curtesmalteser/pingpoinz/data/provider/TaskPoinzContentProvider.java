package com.curtesmalteser.pingpoinz.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.curtesmalteser.pingpoinz.data.db.EventDbModel;
import com.curtesmalteser.pingpoinz.data.db.PoinzDao;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;

import timber.log.Timber;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class TaskPoinzContentProvider extends ContentProvider {

    private PoinzDao mPoinsDao;
    private PoinzDatabase mDb;

    // The authority of the content provider
    public static final String AUTHORITY = "com.curtesmalteser.pingpoinz.data.provider";

    // Build the URI for the events_table
    public static final Uri URI_EVENTS = Uri.parse(
            "content://" + AUTHORITY + "/" + EventDbModel.TABLE_NAME);

    // Define final integer for the directory EVENTS_TABLE
    public static final int EVENTS_TABLE = 100;

    // Define final integer for the directory EVENTS_TABLE
    public static final int EVENTS_SINGLE_ITEM = 101;

    // Add the URI matcher
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, EventDbModel.TABLE_NAME, EVENTS_TABLE);
        MATCHER.addURI(AUTHORITY, EventDbModel.TABLE_NAME + "/*", EVENTS_SINGLE_ITEM);
    }



    @Override
    public boolean onCreate() {
        // Instantiate Room DB
        mDb = PoinzDatabase.getDatabase(getContext());
        mPoinsDao = mDb.poinzDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == EVENTS_TABLE || code == EVENTS_SINGLE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            PoinzDao currenciesDao = PoinzDatabase.getDatabase(context).poinzDao();
            final Cursor cursor;
            if (code == EVENTS_TABLE) {
                cursor = currenciesDao.selectAll();
                Timber.tag("AJDB").d( "query: %s", cursor.getCount());
            } else {
                cursor = currenciesDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
