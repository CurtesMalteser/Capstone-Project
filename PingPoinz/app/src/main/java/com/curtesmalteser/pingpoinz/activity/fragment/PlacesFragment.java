package com.curtesmalteser.pingpoinz.activity.fragment;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.PlacesModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import butterknife.BindView;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

    private static final String TAG = PlacesFragment.class.getSimpleName();


    // The entry points to the Places API.
    private PlaceDetectionClient mPlaceDetectionClient;

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
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;


    ArrayList<PlacesModel> myPlacesArrayList = new ArrayList<>();

    @BindView(R.id.btnShowCurrentPlace)
    AppCompatButton btnShowCurrentPlace;


    public PlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Prompt the user for permission.
        getLocationPermission();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
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
                            /*int count;
                            if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                count = likelyPlaces.getCount();
                            } else {
                                count = M_MAX_ENTRIES;
                            }*/

                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                                String attributions = placeLikelihood.getPlace().getAttributions() != null ? placeLikelihood.getPlace().getAttributions().toString() : "Attributions Not Available";

                                PlacesModel placesModel = PlacesModel.builder().
                                        setPlaceName(placeLikelihood.getPlace().getName().toString())
                                        .setPlaceAddress(placeLikelihood.getPlace().getAddress().toString())
                                        .setPlaceAttributions(attributions)
                                        .setPlaceLatLng(placeLikelihood.getPlace().getLatLng())
                                        .setPlaceType(placeLikelihood.getPlace().getPlaceTypes())
                                        .build();

                                myPlacesArrayList.add(placesModel);

                                Log.d("AJDB", " - > onComplete: " + myPlacesArrayList.size() + " " + placesModel.placeName());

                            }

                            // Release the place likelihood buffer, to avoid memory leaks.
                            likelyPlaces.release();

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

}

