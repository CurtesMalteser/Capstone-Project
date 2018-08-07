package com.curtesmalteser.pingpoinz.activity.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.AppViewModel;
import com.curtesmalteser.pingpoinz.activity.PoinzDetailsActivity;
import com.curtesmalteser.pingpoinz.activity.adapter.PoinzAdapter;
import com.curtesmalteser.pingpoinz.data.api.Event;
import com.curtesmalteser.pingpoinz.data.db.PoinzDatabase;
import com.curtesmalteser.pingpoinz.data.db.StoreEventsAsync;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class PoinzFragment extends Fragment
        implements PoinzAdapter.ListItemClickListener {

    private PoinzAdapter mPoinzAdapter;

    private AppViewModel mViewModel;

    private ArrayList<Event> eventsModel = new ArrayList<>();

    private PoinzDatabase mDb;

    @BindView(R.id.animationLoader)
    LottieAnimationView animationLoader;

    @BindView(R.id.rvPoinzPlaces)
    RecyclerView mRvPoinzPlaces;

    public PoinzFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);

        // Instantiate Room DB
        mDb = PoinzDatabase.getDatabase(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poinz, container, false);

        ButterKnife.bind(this, v);

        animationLoader.setVisibility(View.VISIBLE);

        // TODO: 22/07/2018 Is Context needed?
        mPoinzAdapter = new PoinzAdapter(getContext(), eventsModel, this);
        mRvPoinzPlaces.setAdapter(mPoinzAdapter);
        mRvPoinzPlaces.setHasFixedSize(true);
        mRvPoinzPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mViewModel.getEvents().observe(this, events -> {
            eventsModel.addAll(events);
            mPoinzAdapter.notifyDataSetChanged();
            animationLoader.setVisibility(GONE);

            if (events != null) {
                new StoreEventsAsync().execute(getActivity(), events);
            }
        });
        return v;
    }

    @Override
    public void onListItemClick(Event event) {
        Intent i = new Intent(getActivity(), PoinzDetailsActivity.class);
        i.putExtra(getResources().getString(R.string.string_extra), event);
        startActivity(i);
    }
}
