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
    void addCurrencies(ArrayList<EventDbModel> currenciesModel);

    @Update(onConflict = REPLACE)
    int updateCurrencies(EventDbModel... currenciesModel);

    @Query("SELECT * FROM " + EventDbModel.TABLE_NAME)
    LiveData<List<EventDbModel>> getAllCurrencies();

    @Query("SELECT * FROM events_table WHERE event_id LIKE :eventId")
    EventDbModel selectSingleCurrency(String eventId);

    @Delete
    int deleteCurrencies(EventDbModel... currenciesModel);

    @Query("DELETE FROM " + EventDbModel.TABLE_NAME)
    int deleteEventsTable();

    //***************** Methods to use with ContentProvider *****************//
    // This methods returns a Cursor to use on CurrenciesContentProvider and select all currencies
    @Query("SELECT * FROM " + EventDbModel.TABLE_NAME)
    Cursor selectAll();

    // Select a cheese by the ID
    // ATTENTION: it has to to take a long id because that's uri parse pass
    @Query("SELECT * FROM " + EventDbModel.TABLE_NAME + " WHERE " + EventDbModel.COLUMN_ID + " = :id")
    Cursor selectById(long id);
}
