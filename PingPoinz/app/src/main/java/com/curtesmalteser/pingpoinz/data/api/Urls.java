
package com.curtesmalteser.pingpoinz.data.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Urls {

    public static TypeAdapter<Urls> typeAdapter(Gson gson) {
        return new AutoValue_Urls.GsonTypeAdapter(gson);
    }

    @SerializedName("raw")
    public abstract String raw();
    @SerializedName("full")
    public abstract String full();
    @SerializedName("regular")
    public abstract String regular();
    @SerializedName("small")
    public abstract String small();
    @SerializedName("thumb")
    public abstract String thumb();

}
