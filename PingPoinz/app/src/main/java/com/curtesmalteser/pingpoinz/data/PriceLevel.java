package com.curtesmalteser.pingpoinz.data;

import android.support.annotation.StringRes;

import com.curtesmalteser.pingpoinz.R;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class PriceLevel {

    public static @StringRes int getPriceLevel(int priceLevel) {
        switch (priceLevel) {

            case 0:
                return R.string.free;
            case 1:
                return R.string.inexpensive;
            case 2:
                return R.string.moderate;
            case 3:
                return R.string.expensive;
            case 4:
                return R.string.very_expensive;
            default:
                return R.string.n_a;
        }
    }

}
