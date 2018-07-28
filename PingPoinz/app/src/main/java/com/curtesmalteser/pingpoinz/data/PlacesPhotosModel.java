package com.curtesmalteser.pingpoinz.data;

import android.graphics.Bitmap;

import com.google.auto.value.AutoValue;

/**
 * Created by António "Curtes Malteser" Bastião on 25/07/2018.
 */
@AutoValue
public abstract class PlacesPhotosModel {

    public static Builder builder() {
        return new AutoValue_PlacesPhotosModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setPlaceId(String placeId);

        public abstract Builder setPlacePhoto(Bitmap placePhoto);

        public abstract Builder setPlacePhotoAttributions(String PhotoAttributions);

        public abstract PlacesPhotosModel build();
    }

    public abstract String placeId();

    public abstract Bitmap placePhoto();

    public abstract String placePhotoAttributions();
}
