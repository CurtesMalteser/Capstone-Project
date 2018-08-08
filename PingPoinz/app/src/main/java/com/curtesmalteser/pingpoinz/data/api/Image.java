
package com.curtesmalteser.pingpoinz.data.api;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Image implements Parcelable {

    public static TypeAdapter<Image> typeAdapter(Gson gson) {
        return new AutoValue_Image.GsonTypeAdapter(gson);
    }

    @SerializedName("small")
    @Nullable
    public abstract Small small();

    @SerializedName("width")
    @Nullable
    public abstract String width();

    //@SerializedName("caption")
    //@Nullable
    //public abstract Object caption();

    @SerializedName("medium")
    @Nullable
    public abstract Medium medium();

    @SerializedName("url")
    @Nullable
    public abstract String url();

    @SerializedName("thumb")
    @Nullable
    public abstract Thumb thumb();

    @SerializedName("height")
    @Nullable
    public abstract String height();

}
