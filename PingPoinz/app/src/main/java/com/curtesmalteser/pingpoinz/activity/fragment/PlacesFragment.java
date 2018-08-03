package com.curtesmalteser.pingpoinz.activity.fragment;


import android.arch.lifecycle.ViewModelProviders;
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

    private  AppViewModel mModel;

    // The entry points to the Places API.
    private PlaceDetectionClient mPlaceDetectionClient;

    private GeoDataClient mGeoDataClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;


    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 40;

    ArrayList<PlacesModel> mPlacesArrayList = new ArrayList<>();


    private PlacesAdapter mPoinzPlacesAdapter;

    private PlacesModel placesModel;

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

        mModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        this.mGeoDataClient = Places.getGeoDataClient(getActivity());


        // Prompt the user for permission.
        getLocationPermission();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, v);

        animationLoader.setVisibility(View.VISIBLE);

        // TODO: 22/07/2018 Is Context needed?
        mPoinzPlacesAdapter = new PlacesAdapter(getContext(), mPlacesArrayList, this);
        mRvPoinzPlaces.setAdapter(mPoinzPlacesAdapter);
        mRvPoinzPlaces.setHasFixedSize(true);
        mRvPoinzPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCurrentPlace();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        if (mLastKnownLocation != null) {

                            // TODO: 22/07/2018 Make something with the last known position
                        }
                    } else {
                        Timber.d("Current location is null. Using defaults.");
                        Timber.e(task.getException().toString(), "Exception: %s");
                    }
                });
            } else {
                // TODO: 22/07/2018 Think about something useful
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Timber.e(e);
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

    }

    /**
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void showCurrentPlace() {

        if (mLocationPermissionGranted) {


            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                            // Set the count, handling cases where less than 5 entries are returned.

                            // TODO: 22/07/2018 check if number of places should be limited
                            // TODO: 22/07/2018 based of User Preferences?
                            //int count;
                           /* if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                count = likelyPlaces.getCount();
                            } else {
                                count = M_MAX_ENTRIES;
                            }*/

                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                                final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeLikelihood.getPlace().getId());
                                photoMetadataResponse.addOnCompleteListener(task1 -> {

                                    int string = PriceLevel.getPriceLevel(placeLikelihood.getPlace().getPriceLevel());
                                    String stringPriceLevel = getString(string);

                                    String attributions = placeLikelihood.getPlace().getAttributions() != null ? placeLikelihood.getPlace().getAttributions().toString() : "Attributions Not Available";

                                    placesModel = PlacesModel.builder()
                                            .setPlaceId(placeLikelihood.getPlace().getId())
                                            .setPlaceName(placeLikelihood.getPlace().getName().toString())
                                            .setPlaceAddress(placeLikelihood.getPlace().getAddress().toString())
                                            .setPlacePriceLevel(stringPriceLevel)
                                            .setPlaceRating(placeLikelihood.getPlace().getRating())
                                            .setPlaceAttributions(attributions)
                                            .setPlaceLatLng(placeLikelihood.getPlace().getLatLng())
                                            .setPlaceType(placeLikelihood.getPlace().getPlaceTypes())
                                            .build();
                                    mPlacesArrayList.add(placesModel);
                                    mPoinzPlacesAdapter.notifyDataSetChanged();


                                    // photoMetadataBuffer.release();
                                });

                                animationLoader.setVisibility(GONE);


                            }

                            // Release the place likelihood buffer, to avoid memory leaks.
                            //likelyPlaces.release();

                        } else {
                            Timber.e(task.getException().getMessage(), "Exception: %s");
                        }
                    });
        } else {

            // TODO: 22/07/2018 Think about something useful
            getLocationPermission();

            // The user has not granted permission.
            Timber.i("The user did not grant location permission.");

        }
    }

    @Override
    //public void onListItemClick(ComposedPlacesModel moviesModel) {
    public void onListItemClick(PlacesModel moviesModel) {
        Toast.makeText(getActivity(), moviesModel.placeName(), Toast.LENGTH_SHORT).show();
    }
}

