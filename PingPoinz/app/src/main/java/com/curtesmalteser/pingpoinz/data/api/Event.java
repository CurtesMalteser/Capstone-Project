
package com.curtesmalteser.pingpoinz.data.api;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Event implements Parcelable {
    public static TypeAdapter<Event> typeAdapter(Gson gson) {
        return new AutoValue_Event.GsonTypeAdapter(gson);
    }

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

    @SerializedName("all_day")
    @Nullable
    public abstract Integer allDay();

    @SerializedName("latitude")
    @Nullable
    public abstract Float latitude();

    @SerializedName("longitude")
    @Nullable
    public abstract Float longitude();

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

    @SerializedName("description")
    @Nullable
    public abstract String description();


    @SerializedName("modified")
    @Nullable
    public abstract String modified();

    @SerializedName("venue_display")
    @Nullable
    public abstract Boolean venueDisplay();

    @SerializedName("performers")
    @Nullable
    public abstract Performers performers();

    @SerializedName("title")
    @Nullable
    public abstract String title();

    @SerializedName("geocode_type")
    @Nullable
    public abstract String geocodeType();

    @SerializedName("calendars")
    @Nullable
    public abstract Integer calendars();

    @SerializedName("owner")
    @Nullable
    public abstract String owner();

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
