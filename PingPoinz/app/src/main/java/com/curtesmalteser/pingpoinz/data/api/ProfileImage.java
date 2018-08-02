
package com.curtesmalteser.pingpoinz.data.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ProfileImage {

    public static TypeAdapter<ProfileImage> typeAdapter(Gson gson) {
        return new AutoValue_ProfileImage.GsonTypeAdapter(gson);
    }

    @SerializedName("small")
    public abstract String small();

    @SerializedName("medium")
    public abstract String medium();

    @SerializedName("large")
    public abstract String large();

}
