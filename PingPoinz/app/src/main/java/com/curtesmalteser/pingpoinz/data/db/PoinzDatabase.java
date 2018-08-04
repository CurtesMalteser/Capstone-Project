package com.curtesmalteser.pingpoinz.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
@Database(entities = {EventDbModel.class}, version = 1)
public abstract class PoinzDatabase extends RoomDatabase {

    private static PoinzDatabase INSTANCE;

    public static PoinzDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PoinzDatabase.class,
                    "ping_poinz.db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PoinzDao poinzDao();

}
