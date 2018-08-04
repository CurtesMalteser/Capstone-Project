package com.curtesmalteser.pingpoinz.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
@Entity(tableName = EventDbModel.TABLE_NAME)
public class EventDbModel {

    public static final String TABLE_NAME = "events_table";

    // Set the name of ID column
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true) //By default is false. I left it to make the code clear.
    @ColumnInfo(index = true, name = COLUMN_ID)
    int _id;

    @ColumnInfo(name = "comment_count")
    private Integer commentCount;

    @ColumnInfo(name = "region_abbr")
    private String regionAbbr;

    @ColumnInfo(name = "postal_code")
    private String postalCode;

    /*
     * A false value (0) indicates that the start_time and stop_time are as listed.
     * If the all_day flag is set to 1 (all day) or 2 (no time specified),
     * then the tim
     * e should be omitted from start_time and stop_time.*/
    @ColumnInfo(name = "all_day")
    private Integer allDay;

    @ColumnInfo(name = "latitude")
    private Float latitude;

    @ColumnInfo(name = "longitude")
    private Float longitude;

    @ColumnInfo(name = "event_id")
    private String eventId;

    @ColumnInfo(name = "city_name")
    private String cityName;

    @ColumnInfo(name = "country_name")
    private String countryName;

    @ColumnInfo(name = "country_abbr")
    private String countryAbbr;

    @ColumnInfo(name = "region_name")
    private String regionName;

    @ColumnInfo(name = "start_time")
    private String startTime;

    @ColumnInfo(name = "venue_display")
    private Boolean venueDisplay;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "geocode_type")
    private String geocodeType;

    @ColumnInfo(name = "owner")
    private String owner;

    @ColumnInfo(name = "country_abbr2")
    private String countryAbbr2;

    @ColumnInfo(name = "venue_id")
    private String venueId;

    @ColumnInfo(name = "stop_time")
    private String stopTime;

    @ColumnInfo(name = "venue_name")
    private String venueName;

}
