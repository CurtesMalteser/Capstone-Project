package com.curtesmalteser.pingpoinz.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;

import timber.log.Timber;

public class PlaceDetailsActivity extends AppCompatActivity {

    private AppViewModel mViewModel;
    private PlacesModel mPlacesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        setContentView(R.layout.activity_place_details);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra_place))) {
            mViewModel.setPlaceModel(getIntent().getParcelableExtra(getResources().getString(R.string.string_extra_place)));
        }

        mViewModel.getPlaceModel().observe(this, placesModel -> {
            mPlacesModel = placesModel;
            Timber.tag("foo").d(mPlacesModel.placeName());

        });
    }
}
