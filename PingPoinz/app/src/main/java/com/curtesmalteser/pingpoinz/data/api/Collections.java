package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
@AutoValue
public abstract class Collections {

    public static TypeAdapter<Collections> typeAdapter(Gson gson) {
        return new AutoValue_Collections.GsonTypeAdapter(gson);
    }

    @SerializedName("count")

    public abstract Integer count();


    @SerializedName("overflow")
    @Nullable
    public abstract Boolean overflow();

    @SerializedName("previous")
    @Nullable
    public abstract Object previous();

    @SerializedName("next")
    @Nullable
    public abstract String next();
    @SerializedName("results")

    public abstract ArrayList<Result> results();

}
