
package com.curtesmalteser.pingpoinz.data.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class User {

    public static TypeAdapter<User> typeAdapter(Gson gson) {
        return new AutoValue_User.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract String id();
    @SerializedName("username")
    public abstract String username();
    @SerializedName("name")
    public abstract String name();
    @SerializedName("first_name")
    public abstract String portfolioUrl();
    @SerializedName("profile_image")
    public abstract ProfileImage profileImage();

}
