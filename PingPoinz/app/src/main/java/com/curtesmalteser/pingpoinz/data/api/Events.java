
package com.curtesmalteser.pingpoinz.data.api;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Events {

    @SerializedName("event")
    public abstract ArrayList<Event> event();

    public static TypeAdapter<Events> typeAdapter(Gson gson) {
        return new AutoValue_Events.GsonTypeAdapter(gson);
    }

}
