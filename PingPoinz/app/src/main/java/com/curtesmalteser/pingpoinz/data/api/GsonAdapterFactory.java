package com.curtesmalteser.pingpoinz.data.api;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
@SuppressWarnings("WeakerAccess")
@GsonTypeAdapterFactory
public abstract class GsonAdapterFactory implements TypeAdapterFactory {
    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
        return new AutoValueGson_GsonAdapterFactory();
    }
}