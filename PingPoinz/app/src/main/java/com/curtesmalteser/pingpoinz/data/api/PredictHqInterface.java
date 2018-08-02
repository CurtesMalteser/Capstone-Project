package com.curtesmalteser.pingpoinz.data.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
public interface PredictHqInterface {


    // Ref to make custom queries
    /*@GET("events/search")
    Call<EventfulEventsModel> getEventfulEvents(@QueryMap Map<String, String> queryParams); */


    @GET("events/?start.gte=2018-01-01&&within=100km@-36.844480,174.768368")
    Call<Collections> getHqEvents();
}
