package com.curtesmalteser.pingpoinz.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
@AutoValue
public abstract class PlacesModel {

    public static Builder builder() {
        return new AutoValue_PlacesModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setPlaceName(String placeName);
        public abstract Builder setPlaceAddress(String placeAddress);
        public abstract Builder setPlaceAttributions(String placeAttributions);
        public abstract Builder setPlaceLatLng(LatLng placeLatLng);
        public abstract Builder setPlaceType(List<Integer> placeType);
        public abstract PlacesModel build();
    }

    public abstract String placeName();

    public abstract String placeAddress();

    public abstract String placeAttributions();

    public abstract LatLng placeLatLng();

    public abstract List<Integer> placeType();


}
