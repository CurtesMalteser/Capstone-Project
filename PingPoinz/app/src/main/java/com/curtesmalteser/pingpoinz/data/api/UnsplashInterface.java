package com.curtesmalteser.pingpoinz.data.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
public interface UnsplashInterface {

    @GET("search/photos")
    Call<SearchPhotoResult> getEventfulEvents(@QueryMap Map<String, String> queryParams);

}
