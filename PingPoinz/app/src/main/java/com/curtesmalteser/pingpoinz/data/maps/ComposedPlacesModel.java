package com.curtesmalteser.pingpoinz.data.maps;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 25/07/2018.
 */
@SuppressWarnings("unused")
@AutoValue
public abstract class ComposedPlacesModel {

    public static Builder builder() {
        return new AutoValue_ComposedPlacesModel.Builder();
    }

    @SuppressWarnings("unused")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setPlaceId(String placeId);
        public abstract Builder setPlaceName(String placeName);
        public abstract Builder setPlaceAddress(String placeAddress);
        public abstract Builder setPlacePriceLevel(String placePriceLevel);
        public abstract Builder setPlaceRating(Float placeRating);
        public abstract Builder setPlaceAttributions(String placeAttributions);
        public abstract Builder setPlaceLatLng(LatLng placeLatLng);
        public abstract Builder setPlaceType(List<Integer> placeType);
        public abstract Builder setPlacePhoto(Bitmap placePhoto);
        public abstract Builder setPlacePhotoAttributions(String PhotoAttributions);
        public abstract ComposedPlacesModel build();
    }

    public abstract String placeId();

    public abstract String placeName();

    public abstract String placeAddress();

    public abstract String placePriceLevel();

    public abstract Float placeRating();

    public abstract String placeAttributions();

    public abstract LatLng placeLatLng();

    public abstract List<Integer> placeType();

    public abstract Bitmap placePhoto();

    public abstract String placePhotoAttributions();
}
