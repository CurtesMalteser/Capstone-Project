package com.curtesmalteser.pingpoinz.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.curtesmalteser.pingpoinz.BuildConfig;
import com.curtesmalteser.pingpoinz.data.api.Event;
import com.curtesmalteser.pingpoinz.data.api.EventfulApiClient;
import com.curtesmalteser.pingpoinz.data.api.EventfulApiInterface;
import com.curtesmalteser.pingpoinz.data.api.EventfulEventsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.view.View.GONE;

/**
 * Created by António "Curtes Malteser" Bastião on 03/08/2018.
 */
public class AppViewModel extends ViewModel {

    private MutableLiveData<List<Event>> eventsList;

    public LiveData<List<Event>> getEvents() {
        if (eventsList == null) {
            eventsList = new MutableLiveData<List<Event>>();
            makeMoviesQuery();
        }
        return eventsList;
    }

    private void makeMoviesQuery() {
        EventfulApiInterface apiInterface = EventfulApiClient.getClient().create(EventfulApiInterface.class);
        Call<EventfulEventsModel> call;

        // TODO: 29/07/2018 -> check for connectivity
       /* if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {*/

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", "Future");
        queryParams.put("location", "Valais");
        queryParams.put("app_key", BuildConfig.EVENTFUL_KEY);
        queryParams.put("page_number", "1");
        queryParams.put("page_size", "250");

        call = apiInterface.getEventfulEvents(queryParams);
        call.enqueue(new Callback<EventfulEventsModel>() {
            @Override
            public void onResponse(@NonNull Call<EventfulEventsModel> call, @NonNull Response<EventfulEventsModel> response) {
                if (response.body().events().eventsList() != null) {
                    eventsList.postValue(response.body().events().eventsList());
                }
                for (Event event : response.body().events().eventsList()) {
                    if (event.image() != null)
                        Timber.d("foo this -> " + event.image().url() + event.title());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventfulEventsModel> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
        // TODO: 29/07/2018 -> else of check for connectivity
       /* } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();*/
    }
}
