package com.curtesmalteser.pingpoinz.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
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
import com.curtesmalteser.pingpoinz.data.db.StoreEventsAsync;
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
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private boolean mRequestingLocationUpdates = false;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private PlaceDetectionClient mPlaceDetectionClient;

    private PlacesModel placesModel;

    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle mFirebaseBundle;

    private List<PlacesModel> mPlacesArrayList = new ArrayList();
    private AppViewModel mViewModel;

    private ConnectivityManager mConnectivityManager;

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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager != null) {

            if (mConnectivityManager.getActiveNetworkInfo() != null
                    && mConnectivityManager.getActiveNetworkInfo().isAvailable()
                    && mConnectivityManager.getActiveNetworkInfo().isConnected()) {
            } else {
                mViewModel.setIsConnected(false);
            }
        }


        mFirebaseBundle = new Bundle();

        btnOpenMapsActivity.bringToFront();
        btnOpenMapsActivity.setOnClickListener(l -> {
            Intent i = new Intent(this, MapActivity.class);
            startActivity(i);
            firebaseAnalyticeLog(mFirebaseBundle, FirebaseAnalytics.Param.ITEM_NAME, "MapActivity");
        });

        MapsFragmentPagerAdapter adapter =
                new MapsFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        getLocationPermission();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        });

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(MainActivity.this,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
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
                    firebaseAnalyticeLog(mFirebaseBundle, FirebaseAnalytics.Param.LOCATION, location.toString());
                }
            }

        };

        showCurrentPlace();
    }

    private void firebaseAnalyticeLog(Bundle bundle, String param, String event) {
        mFirebaseBundle.putString(param, event);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
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

    private void getLocationPermission() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void showCurrentPlace() {

        if (mLocationPermissionGranted) {
            @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
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
                            likelyPlaces.release();
                            Timber.e(task.getException());
                        }
                    });
        } else {
            getLocationPermission();
            Timber.i("The user did not grant location permission.");
        }
    }

}
