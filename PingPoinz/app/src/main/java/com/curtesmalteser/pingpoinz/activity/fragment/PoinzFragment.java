package com.curtesmalteser.pingpoinz.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtesmalteser.pingpoinz.BuildConfig;
import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.PoinzDetailsActivity;
import com.curtesmalteser.pingpoinz.activity.adapter.PoinzAdapter;
import com.curtesmalteser.pingpoinz.data.api.Event;
import com.curtesmalteser.pingpoinz.data.api.EventfulApiClient;
import com.curtesmalteser.pingpoinz.data.api.EventfulApiInterface;
import com.curtesmalteser.pingpoinz.data.api.EventfulEventsModel;
import com.curtesmalteser.pingpoinz.data.api.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class PoinzFragment extends Fragment
        implements PoinzAdapter.ListItemClickListener {

    private PoinzAdapter mPoinzAdapter;

    private ArrayList<Event> eventsModel = new ArrayList<>();

    @BindView(R.id.rvPoinzPlaces)
    RecyclerView mRvPoinzPlaces;

    public PoinzFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_poinz, container, false);

        ButterKnife.bind(this, v);

        // TODO: 22/07/2018 Is Context needed?
        mPoinzAdapter = new PoinzAdapter(getContext(), eventsModel, this);
        mRvPoinzPlaces.setAdapter(mPoinzAdapter);
        mRvPoinzPlaces.setHasFixedSize(true);
        mRvPoinzPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        makeMoviesQuery(0);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void makeMoviesQuery(int page) {
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
                eventsModel.addAll(response.body().events().eventsList());
                mPoinzAdapter.notifyDataSetChanged();

               for(Event event : response.body().events().eventsList()) {
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

    @Override
    public void onListItemClick(Event event) {

            Intent i = new Intent(getActivity(), PoinzDetailsActivity.class);
            i.putExtra(getResources().getString(R.string.string_extra), event);
            startActivity(i);
            }
}
