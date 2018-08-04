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

    @Ignore
    public EventDbModel(Integer commentCount, String regionAbbr, String postalCode, Integer allDay, Float latitude, Float longitude, String eventId, String cityName, String countryName, String countryAbbr, String regionName, String startTime, Boolean venueDisplay, String title, String geocodeType, String owner, String countryAbbr2, String venueId, String stopTime, String venueName) {
        this.commentCount = commentCount;
        this.regionAbbr = regionAbbr;
        this.postalCode = postalCode;
        this.allDay = allDay;
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
        this.geocodeType = geocodeType;
        this.owner = owner;
        this.countryAbbr2 = countryAbbr2;
        this.venueId = venueId;
        this.stopTime = stopTime;
        this.venueName = venueName;
    }

    public EventDbModel(int id, Integer commentCount, String regionAbbr, String postalCode, Integer allDay, Float latitude, Float longitude, String eventId, String cityName, String countryName, String countryAbbr, String regionName, String startTime, Boolean venueDisplay, String title, String geocodeType, String owner, String countryAbbr2, String venueId, String stopTime, String venueName) {
        this.id = id;
        this.commentCount = commentCount;
        this.regionAbbr = regionAbbr;
        this.postalCode = postalCode;
        this.allDay = allDay;
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
        this.geocodeType = geocodeType;
        this.owner = owner;
        this.countryAbbr2 = countryAbbr2;
        this.venueId = venueId;
        this.stopTime = stopTime;
        this.venueName = venueName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
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

    public Integer getAllDay() {
        return allDay;
    }

    public void setAllDay(Integer allDay) {
        this.allDay = allDay;
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

    public String getGeocodeType() {
        return geocodeType;
    }

    public void setGeocodeType(String geocodeType) {
        this.geocodeType = geocodeType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCountryAbbr2() {
        return countryAbbr2;
    }

    public void setCountryAbbr2(String countryAbbr2) {
        this.countryAbbr2 = countryAbbr2;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
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
