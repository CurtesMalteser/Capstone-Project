package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Performer {

    public static TypeAdapter<Performer> typeAdapter(Gson gson) {
        return new AutoValue_Performer.GsonTypeAdapter(gson);
    }

    @SerializedName("creator")
    @Nullable
    public abstract String creator();

    @SerializedName("linker")
    @Nullable
    public abstract String linker();

    @SerializedName("name")
    @Nullable
    public abstract String name();

    @SerializedName("url")
    @Nullable
    public abstract String url();

    @SerializedName("id")
    @Nullable
    public abstract String id();

    @SerializedName("short_bio")
    @Nullable
    public abstract String shortBio();
}