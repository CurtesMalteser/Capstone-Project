package com.curtesmalteser.pingpoinz.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.adapter.PoinzAdapter;
import com.curtesmalteser.pingpoinz.data.api.Collections;
import com.curtesmalteser.pingpoinz.data.api.PredictHqClient;
import com.curtesmalteser.pingpoinz.data.api.PredictHqInterface;
import com.curtesmalteser.pingpoinz.data.api.Result;

import java.util.ArrayList;

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

    private ArrayList<Result> eventsModel = new ArrayList<>();

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
        PredictHqInterface apiInterface = PredictHqClient.getClient().create(PredictHqInterface.class);
        Call<Collections> call;


        call = apiInterface.getHqEvents();
        call.enqueue(new Callback<Collections>() {
            @Override
            public void onResponse(@NonNull Call<Collections> call, @NonNull Response<Collections> response) {


                eventsModel.addAll(response.body().results());
                mPoinzAdapter.notifyDataSetChanged();

                for (Result result : response.body().results()) {
                    Timber.d("XPTO " + result.toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Collections> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }

    @Override
    public void onListItemClick(Result event) {
        // TODO: 29/07/2018 -> add click
    }
}
