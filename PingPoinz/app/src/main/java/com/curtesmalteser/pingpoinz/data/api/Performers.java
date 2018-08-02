package com.curtesmalteser.pingpoinz.data.api;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Performers implements Parcelable {

    public static TypeAdapter<Performers> typeAdapter(Gson gson) {
        return new AutoValue_Performers.GsonTypeAdapter(gson);
    }

    @SerializedName("performer")
    @Nullable
    public abstract Performer performer();
}