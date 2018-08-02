package com.curtesmalteser.pingpoinz.data.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
@AutoValue
public abstract class PhotoResult {

    public static TypeAdapter<PhotoResult> typeAdapter(Gson gson) {
        return new AutoValue_PhotoResult.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract String id();

    @SerializedName("user")
    public abstract User user();

    @SerializedName("urls")
    public abstract Urls urls();
}
