package com.curtesmalteser.pingpoinz.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
@Dao
public interface PoinzDao {
    @Insert(onConflict = REPLACE)
    void addEvents(ArrayList<EventDbModel> eventsModel);

    @Query("SELECT * FROM " + EventDbModel.TABLE_NAME)
    LiveData<List<EventDbModel>> getAllEvents();

    @Query("DELETE FROM " + EventDbModel.TABLE_NAME)
    int deleteEventsTable();}
