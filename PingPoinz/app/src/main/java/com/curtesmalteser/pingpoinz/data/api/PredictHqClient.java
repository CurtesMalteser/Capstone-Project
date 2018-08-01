package com.curtesmalteser.pingpoinz.data.api;

import com.curtesmalteser.pingpoinz.BuildConfig;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by António "Curtes Malteser" Bastião on 01/08/2018.
 */
public class PredictHqClient {

    //https://api.predicthq.com/{api-version}/{resource}/
    private static final String BASE_URL = "https://api.predicthq.com/v1/";

    private static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
            new GsonBuilder().registerTypeAdapterFactory(GsonAdapterFactory.create())
                    .create());

    public static Retrofit getClient() {

       /* Accept: application/json
        Authorization: Bearer $ACCESS_TOKEN*/


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException {
                                          Request original = chain.request();

                                          Request request = original.newBuilder()
                                                  .header("Accept", "application/json")
                                                  .header("Authorization", "Bearer " + BuildConfig.PREDICT_HQ_TOKEN)
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });


        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();




    }


}
