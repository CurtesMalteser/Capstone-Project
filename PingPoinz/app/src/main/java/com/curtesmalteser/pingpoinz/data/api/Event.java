
package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Event {

    public static TypeAdapter<Event> typeAdapter(Gson gson) {
        return new AutoValue_Event.GsonTypeAdapter(gson);
    }

    // Todo -> Check "Object" and if a model is needed
    @SerializedName("watching_count")
    @Nullable
    public abstract Object watchingCount();


    @SerializedName("olson_path")

    @Nullable

    public abstract String olsonPath();


    @SerializedName("calendar_count")

    @Nullable

    public abstract Integer calendarCount();


    @SerializedName("comment_count")

    @Nullable

    public abstract Integer commentCount();


    @SerializedName("region_abbr")

    @Nullable

    public abstract String regionAbbr();


    @SerializedName("postal_code")

    @Nullable

    public abstract String postalCode();


    @SerializedName("going_count")

    @Nullable

    public abstract Object goingCount();

    /*
     * A false value (0) indicates that the start_time and stop_time are as listed.
     * If the all_day flag is set to 1 (all day) or 2 (no time specified),
     * then the tim
     * e should be omitted from start_time and stop_time.*/
    @SerializedName("all_day")

    @Nullable

    public abstract Integer allDay();


    @SerializedName("latitude")

    @Nullable

    public abstract Float latitude();


    @SerializedName("longitude")

    @Nullable

    public abstract Float longitude();


    @SerializedName("groups")

    @Nullable

    public abstract Object groups();


    @SerializedName("url")

    @Nullable

    public abstract String url();


    @SerializedName("id")

    @Nullable

    public abstract String id();


    @SerializedName("privacy")

    @Nullable

    public abstract String privacy();


    @SerializedName("city_name")

    @Nullable

    public abstract String cityName();


    @SerializedName("link_count")

    @Nullable

    public abstract Integer linkCount();


    @SerializedName("country_name")

    @Nullable

    public abstract String countryName();


    @SerializedName("country_abbr")

    @Nullable

    public abstract String countryAbbr();


    @SerializedName("region_name")

    @Nullable

    public abstract String regionName();


    @SerializedName("start_time")

    @Nullable

    public abstract String startTime();


    @SerializedName("tz_id")

    @Nullable

    public abstract Object tzId();


    @SerializedName("description")

    @Nullable

    public abstract Object description();


    @SerializedName("modified")

    @Nullable

    public abstract String modified();


    @SerializedName("venue_display")

    @Nullable

    public abstract Boolean venueDisplay();


    @SerializedName("tz_country")

    @Nullable

    public abstract Object tzCountry();


    @SerializedName("performers")

    @Nullable

    public abstract Performers performers();


    @SerializedName("title")

    @Nullable

    public abstract String title();


    @SerializedName("venue_address")

    @Nullable

    public abstract Object venueAddress();


    @SerializedName("geocode_type")

    @Nullable

    public abstract String geocodeType();


    @SerializedName("tz_olson_path")

    @Nullable

    public abstract Object tzOlsonPath();


    @SerializedName("recur_string")

    @Nullable

    public abstract Object recurString();


    @SerializedName("calendars")

    @Nullable

    public abstract Integer calendars();


    @SerializedName("owner")

    @Nullable

    public abstract String owner();


    @SerializedName("going")

    @Nullable

    public abstract Object going();


    @SerializedName("country_abbr2")

    @Nullable

    public abstract String countryAbbr2();


    @SerializedName("image")

    @Nullable

    public abstract Image image();


    @SerializedName("created")

    @Nullable
    public abstract String created();


    @SerializedName("venue_id")

    @Nullable

    public abstract String venueId();

    @SerializedName("tz_city")
    @Nullable
    public abstract Object tzCity();

    @SerializedName("stop_time")
    @Nullable
    public abstract String stopTime();

    @SerializedName("venue_name")
    @Nullable
    public abstract String venueName();

    @SerializedName("venue_url")
    @Nullable
    public abstract String venueUrl();
}
