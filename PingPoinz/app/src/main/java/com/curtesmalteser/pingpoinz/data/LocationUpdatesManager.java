package com.curtesmalteser.pingpoinz.data;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by António "Curtes Malteser" Bastião on 20/07/2018.
 */
public class LocationUpdatesManager {

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private FusedLocationProviderClient mFusedLocationClient;

    private LocationUpdatesDelegate mDelegate;

    private Activity mContext;

    private LocationCallback mLocationCallback;

    public LocationUpdatesManager(Activity context) {
        this.mContext = context;
        this.mDelegate = (LocationUpdatesDelegate) context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    public interface LocationUpdatesDelegate {

        void locationUpdatesOnSuccess(LocationSettingsResponse locationSettingsResponse);

        void delegateLastKnownPosition(Location lastKnowPosition);

        void updatedPosition(Location updatedPosition);

    }

    // ***** Get the last known location *****
    public void setFusedLocationClient() {

        if (checkPermission()) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(mContext, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mDelegate.delegateLastKnownPosition(location);
                            }
                        }
                    });
        }
    }

    private boolean checkPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    // ***** End of Get the last known location *****

    // ***** Change location settings *****
    private LocationSettingsRequest.Builder locationSettingsBuilder() {
        return new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

    }

    private LocationRequest createLocationRequest() {
        return new LocationRequest()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void locationSettings() {

        SettingsClient client = LocationServices.getSettingsClient(mContext);

        Task<LocationSettingsResponse> task = client.checkLocationSettings(locationSettingsBuilder().build());

        task.addOnSuccessListener(mContext, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                LocationSettingsStates x = locationSettingsResponse.getLocationSettingsStates();


                mDelegate.locationUpdatesOnSuccess(locationSettingsResponse);
            }
        });

        task.addOnFailureListener(mContext, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(mContext,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    // ***** End of Change location settings *****

    // ***** Receive location updates *****

    public void startLocationUpdates() {
        if (checkPermission()) {
            mFusedLocationClient.requestLocationUpdates(createLocationRequest(),
                    mLocationCallback,
                    null /* Looper */);
        }
    }

    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void updateLocation() {
        this.mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...

                    mDelegate.updatedPosition(location);
                }
            }
        };
    }


    // ***** End of Receive location updates *****


}
