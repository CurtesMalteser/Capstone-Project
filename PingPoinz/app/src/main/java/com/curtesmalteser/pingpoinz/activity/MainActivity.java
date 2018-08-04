package com.curtesmalteser.pingpoinz.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.adapter.MapsFragmentPagerAdapter;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;
import com.curtesmalteser.pingpoinz.data.maps.PriceLevel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    // todo -> make the only portrait
    // TODO: 02/08/2018 -> make get the position from the activity and pass it into the fragments 

    // Variables to access position since, the Google Maps API requires ActivityContext
    // Even if this will generate a horrible overcrowded activity
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private boolean mRequestingLocationUpdates = false;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mKnownLocation;
    
    // The entry points to the Places API.
    private PlaceDetectionClient mPlaceDetectionClient;

    private PlacesModel placesModel;


    // End of variables to access position since, the Google Maps API requires ActivityContext

    private List<PlacesModel> mPlacesArrayList = new ArrayList();
    private AppViewModel mViewModel;


    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.btnOpenMapsActivity)
    FloatingActionButton btnOpenMapsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnOpenMapsActivity.bringToFront();
        btnOpenMapsActivity.setOnClickListener(l -> {
            Intent i = new Intent(this, MapActivity.class);
            startActivity(i);
        });


        MapsFragmentPagerAdapter adapter =
                new MapsFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // ************* Maps Utils *****************************//

        // Prompt the user for permission.
        getLocationPermission();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);


        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

                mRequestingLocationUpdates = true;
                startLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...

                    // TODO: 04/08/2018 -> annotation one.a) 
                    mKnownLocation = location;
                    Timber.d("AJDB -> lat: " + location.getLatitude() + " long: " + location.getLongitude());

                    // TODO: 04/08/2018 -> LiveData to update location and showCurrentPlace(); annotation Two
                }
            }

        };


        // TODO: 04/08/2018 -> check where show places fits better
        /* annotation Two -> Probably on onLocationResult(), but a method to update the places only when the distance
        *  to the previous point is > than X
        */

        showCurrentPlace();

        // ************* EndMaps Utils *****************************//

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (checkPermission()) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean checkPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
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
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
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

                                int string = PriceLevel.getPriceLevel(placeLikelihood.getPlace().getPriceLevel());
                                String stringPriceLevel = getString(string);

                                String attributions = placeLikelihood.getPlace().getAttributions() != null ? placeLikelihood.getPlace().getAttributions().toString() : "Attributions Not Available";
                                String address = placeLikelihood.getPlace().getAddress() != null ? placeLikelihood.getPlace().getAddress().toString() : "Address Not Available";

                                placesModel = PlacesModel.builder()
                                        .setPlaceId(placeLikelihood.getPlace().getId())
                                        .setPlaceName(placeLikelihood.getPlace().getName().toString())
                                        .setPlaceAddress(address)
                                        .setPlacePriceLevel(stringPriceLevel)
                                        .setPlaceRating(placeLikelihood.getPlace().getRating())
                                        .setPlaceAttributions(attributions)
                                        .setPlaceLatLng(placeLikelihood.getPlace().getLatLng())
                                        .setPlaceType(placeLikelihood.getPlace().getPlaceTypes())
                                        .build();


                                mPlacesArrayList.add(placesModel);
                                mViewModel.setPlaces(mPlacesArrayList);

                            }

                            // Release the place likelihood buffer, to avoid memory leaks.
                            likelyPlaces.release();

                        } else {
                            Timber.e(task.getException().getMessage(), "Exception: %s");
                        }
                    });
        } else {

            getLocationPermission();

            // The user has not granted permission.
            Timber.i("The user did not grant location permission.");

        }
    }

}
