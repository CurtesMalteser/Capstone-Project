
package com.curtesmalteser.pingpoinz.data.api;

import android.support.annotation.Nullable;

import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class SearchPhotoResult {

    public static TypeAdapter<SearchPhotoResult> typeAdapter(Gson gson) {
        return new AutoValue_SearchPhotoResult.GsonTypeAdapter(gson);
    }

    @SerializedName("total")
    public abstract Integer total();

    @SerializedName("total_pages")
    public abstract Integer totalPages();

    @SerializedName("results")
    @Nullable
    public abstract List<PhotoResult> results();

}
