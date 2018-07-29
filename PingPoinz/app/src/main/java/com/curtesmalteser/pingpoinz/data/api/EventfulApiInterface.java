package com.curtesmalteser.pingpoinz.data.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
public interface EventfulApiInterface {

    @GET("events/search")
    Call<EventfulEventsModel> getEventfulEvents(@QueryMap Map<String, String> queryParams);
}
