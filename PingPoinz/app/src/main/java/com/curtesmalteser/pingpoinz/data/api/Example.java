
package com.curtesmalteser.pingpoinz.data.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("last_item")
    @Expose
    public Object lastItem;
    @SerializedName("total_items")
    @Expose
    public String totalItems;
    @SerializedName("first_item")
    @Expose
    public Object firstItem;
    @SerializedName("page_number")
    @Expose
    public String pageNumber;
    @SerializedName("page_size")
    @Expose
    public String pageSize;
    @SerializedName("page_items")
    @Expose
    public Object pageItems;
    @SerializedName("search_time")
    @Expose
    public String searchTime;
    @SerializedName("page_count")
    @Expose
    public String pageCount;
    @SerializedName("events")
    @Expose
    public Events events;

}
