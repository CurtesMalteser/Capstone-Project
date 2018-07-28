
package com.curtesmalteser.pingpoinz.data.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Events {

    @SerializedName("event")
    @Expose
    public List<Event> event = null;

}
