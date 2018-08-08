
package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class EventfulEventsModel {

    public static TypeAdapter<EventfulEventsModel> typeAdapter(Gson gson) {
        return new AutoValue_EventfulEventsModel.GsonTypeAdapter(gson);
    }

    @SerializedName("last_item")
    @Nullable
    public abstract Object lastItem();

    @SerializedName("total_items")
    @Nullable
    public abstract Integer totalItems();

    @SerializedName("first_item")
    @Nullable
    public abstract Integer firstItem();

    @SerializedName("page_number")
    @Nullable
    public abstract Integer pageNumber();

    @SerializedName("page_size")
    @Nullable
    public abstract Integer pageSize();

    @SerializedName("page_items")
    @Nullable
    public abstract Integer pageItems();

    @SerializedName("search_time")
    @Nullable
    public abstract Float searchTime();

    @SerializedName("page_count")
    @Nullable
    public abstract Integer pageCount();

    @SerializedName("events")
    @Nullable
    public abstract Events events();

}
