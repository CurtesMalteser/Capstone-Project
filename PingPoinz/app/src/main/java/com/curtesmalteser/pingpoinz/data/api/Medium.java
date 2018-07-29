
package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Medium {

    public static TypeAdapter<Medium> typeAdapter(Gson gson) {
        return new AutoValue_Medium.GsonTypeAdapter(gson);
    }

    @SerializedName("width")
    @Nullable
    public abstract String width();

    @SerializedName("url")
    @Nullable
    public abstract String url();

    @SerializedName("height")
    @Nullable
    public abstract String height();
}
