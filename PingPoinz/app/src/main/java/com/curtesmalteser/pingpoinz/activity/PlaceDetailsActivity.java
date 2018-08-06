package com.curtesmalteser.pingpoinz.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;
import com.curtesmalteser.pingpoinz.data.maps.PlacesPhotosModel;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PlaceDetailsActivity extends AppCompatActivity {

    private AppViewModel mViewModel;
    private PlacesModel mPlacesModel;
    private GeoDataClient mGeoDataClient;

    @BindView(R.id.poinzPoster)
    AppCompatImageView poinzPoster;

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;

    @BindView(R.id.tvDescription)
    AppCompatTextView tvDescription;

    @BindView(R.id.tvRating)
    AppCompatTextView tvRating;

    @BindView(R.id.tvCity)
    AppCompatTextView tvCity;

    @BindView(R.id.tvStart)
    AppCompatTextView tvStart;

    @BindView(R.id.tvEnd)
    AppCompatTextView tvEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        setContentView(R.layout.activity_place_details);

        ButterKnife.bind(this);

        mGeoDataClient = Places.getGeoDataClient(this);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra_place))) {
            mViewModel.setPlaceModel(getIntent().getParcelableExtra(getResources().getString(R.string.string_extra_place)));
        }

        mViewModel.getPlaceModel().observe(this, placesModel -> {
            mPlacesModel = placesModel;
            tvTitle.setText(mPlacesModel.placeName());
            String rating = mPlacesModel.placeRating() != -1.0f ? String.valueOf(mPlacesModel.placeRating()) : "N/A";
            tvRating.setText(rating);
            tvStart.setText(mPlacesModel.placePriceLevel());
            tvDescription.setText(mPlacesModel.placeAddress());
            tvCity.setText("Don't implemented");
            getPhotos(mPlacesModel.placeId());
        });

        mViewModel.getPlacesPhotosModel().observe(this, placesPhotosModel -> {

            if (placesPhotosModel != null) {
                poinzPoster.setImageBitmap(placesPhotosModel.placePhoto());
            }

            String photoAttributions = placesPhotosModel != null ? placesPhotosModel.placePhotoAttributions() : "N/A";
            tvEnd.setText(photoAttributions);
        });
    }

    private void getPhotos(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(task -> {
            PlacePhotoMetadataResponse photos = task.getResult();
            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0) {
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                CharSequence attribution = photoMetadata.getAttributions() != null ? photoMetadata.getAttributions() : "N/A";
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(task1 -> {
                    PlacePhotoResponse photo = task1.getResult();
                    Bitmap bitmap = photo.getBitmap();

                    mViewModel.setPlacesPhotosModel(
                            PlacesPhotosModel.builder()
                                    .setPlaceId(placeId)
                                    .setPlacePhoto(bitmap)
                                    .setPlacePhotoAttributions(attribution.toString())
                                    .build()
                    );
                });
            } else {
                // TODO: 05/08/2018 -> and a placeholder on else statement
                Timber.d("No images for the place with id %s", placeId);
            }
            photoMetadataBuffer.release();
        });
    }
}
