package com.curtesmalteser.pingpoinz.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;
import com.curtesmalteser.pingpoinz.data.maps.PlacesPhotosModel;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import timber.log.Timber;

public class PlaceDetailsActivity extends AppCompatActivity {

    private AppViewModel mViewModel;
    private PlacesModel mPlacesModel;
    private GeoDataClient mGeoDataClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        setContentView(R.layout.activity_place_details);

        mGeoDataClient = Places.getGeoDataClient(this);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra_place))) {
            mViewModel.setPlaceModel(getIntent().getParcelableExtra(getResources().getString(R.string.string_extra_place)));
        }

        mViewModel.getPlaceModel().observe(this, placesModel -> {
            mPlacesModel = placesModel;
            Timber.tag("foo").d(mPlacesModel.placeName());
            getPhotos(mPlacesModel.placeId());
        });

        mViewModel.getPlacesPhotosModel().observe(this, placesPhotosModel -> {
            String photoAttributions = placesPhotosModel != null ? placesPhotosModel.placePhotoAttributions() : "N/A";
            Timber.tag("foo").d(photoAttributions);
        });
    }

    // Request photos and metadata for the specified place.
    private void getPhotos(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();

                        mViewModel.setPlacesPhotosModel(
                                PlacesPhotosModel.builder()
                                        .setPlaceId(placeId)
                                        .setPlacePhoto(bitmap)
                                        .setPlacePhotoAttributions(attribution.toString())
                                        .build()
                        );
                    }
                });
            }
        });
    }
}
