package com.curtesmalteser.pingpoinz.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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
    int id;

    @ColumnInfo(name = "region_abbr")
    private String regionAbbr;

    @ColumnInfo(name = "postal_code")
    private String postalCode;

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

    @ColumnInfo(name = "stop_time")
    private String stopTime;

    @ColumnInfo(name = "venue_name")
    private String venueName;

    @Ignore
    public EventDbModel(String regionAbbr, String postalCode,
                        Float latitude, Float longitude, String eventId,
                        String cityName, String countryName, String countryAbbr,
                        String regionName, String startTime, Boolean venueDisplay,
                        String title, String stopTime, String venueName) {
        this.regionAbbr = regionAbbr;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventId = eventId;
        this.cityName = cityName;
        this.countryName = countryName;
        this.countryAbbr = countryAbbr;
        this.regionName = regionName;
        this.startTime = startTime;
        this.venueDisplay = venueDisplay;
        this.title = title;
        this.stopTime = stopTime;
        this.venueName = venueName;
    }

    public EventDbModel(int id, String regionAbbr, String postalCode, Float latitude, Float longitude, String eventId, String cityName, String countryName, String countryAbbr, String regionName, String startTime, Boolean venueDisplay, String title, String stopTime, String venueName) {
        this.id = id;
        this.regionAbbr = regionAbbr;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventId = eventId;
        this.cityName = cityName;
        this.countryName = countryName;
        this.countryAbbr = countryAbbr;
        this.regionName = regionName;
        this.startTime = startTime;
        this.venueDisplay = venueDisplay;
        this.title = title;
        this.stopTime = stopTime;
        this.venueName = venueName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionAbbr() {
        return regionAbbr;
    }

    public void setRegionAbbr(String regionAbbr) {
        this.regionAbbr = regionAbbr;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryAbbr() {
        return countryAbbr;
    }

    public void setCountryAbbr(String countryAbbr) {
        this.countryAbbr = countryAbbr;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getVenueDisplay() {
        return venueDisplay;
    }

    public void setVenueDisplay(Boolean venueDisplay) {
        this.venueDisplay = venueDisplay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
