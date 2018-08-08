package com.curtesmalteser.pingpoinz.data.api;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
public class UnsplashClient {

    private static final String BASE_URL = " https://api.unsplash.com/";

    private final static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
            new GsonBuilder().registerTypeAdapterFactory(GsonAdapterFactory.create())
                    .create());

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Accept-Version", "v1")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });


        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();




    }
}
