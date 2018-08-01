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
public abstract class Result {

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract String id();

    @SerializedName("scope")
    public abstract String scope();

    @SerializedName("title")
    public abstract String title();

    @SerializedName("description")
    public abstract String description();

    @SerializedName("category")
    public abstract String category();

    @SerializedName("labels")
    public abstract ArrayList<String> labels();

    @SerializedName("start")
    public abstract String start();

    @SerializedName("end")
    public abstract String end();

    @SerializedName("updated")
    public abstract String updated();

    @SerializedName("first_seen")
    @Nullable
    public abstract String firstSeen();

    @SerializedName("timezone")
    public abstract String timezone();

    @SerializedName("duration")
    public abstract Integer duration();

    @SerializedName("rank")
    public abstract Integer rank();

    @SerializedName("country")
    public abstract String country();

    @SerializedName("location")
    @SuppressWarnings("mutable")
    public abstract double[] location();

    @SerializedName("state")
    public abstract String state();

    @SerializedName("relevance")
    public abstract Object relevance();

}
