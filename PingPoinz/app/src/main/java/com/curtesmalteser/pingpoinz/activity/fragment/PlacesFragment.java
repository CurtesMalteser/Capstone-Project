package com.curtesmalteser.pingpoinz.activity.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.AppViewModel;
import com.curtesmalteser.pingpoinz.activity.PlaceDetailsActivity;
import com.curtesmalteser.pingpoinz.activity.adapter.PlacesAdapter;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;
import com.curtesmalteser.pingpoinz.data.maps.PriceLevel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment
        implements PlacesAdapter.ListItemClickListener {

    private static final String TAG = PlacesFragment.class.getSimpleName();

    private AppViewModel mViewModel;

    ArrayList<PlacesModel> mPlacesArrayList = new ArrayList<>();


    private PlacesAdapter mPoinzPlacesAdapter;

    @BindView(R.id.animationLoader)
    LottieAnimationView animationLoader;

    @BindView(R.id.rvPlacesPlaces)
    RecyclerView mRvPoinzPlaces;

    public PlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, v);

        animationLoader.setVisibility(View.VISIBLE);
        mViewModel.getIsConnected().observe(this, aBoolean -> {
            if (aBoolean != null && !aBoolean) {
                animationLoader.setAnimation(R.raw.no_connection);
            }
        });

        mPoinzPlacesAdapter = new PlacesAdapter(getContext(), mPlacesArrayList, this);
        mRvPoinzPlaces.setAdapter(mPoinzPlacesAdapter);
        mRvPoinzPlaces.setHasFixedSize(true);
        mRvPoinzPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mViewModel.getPlaces().observe(this, placesModels -> {
            mPlacesArrayList.addAll(placesModels);
            mPoinzPlacesAdapter.notifyDataSetChanged();
            animationLoader.setVisibility(GONE);
        });

        return v;
    }

    @Override
    public void onListItemClick(PlacesModel placesModel) {
        Intent i = new Intent(getActivity(), PlaceDetailsActivity.class);
        i.putExtra(getString(R.string.string_extra_place), placesModel);
        startActivity(i);

    }
}

