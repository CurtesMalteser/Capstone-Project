package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.fragment.PlacesFragment;
import com.curtesmalteser.pingpoinz.activity.fragment.PoinzFragment;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class MapsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MapsFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PoinzFragment();
            case 1:
                return new PlacesFragment();
            default:
                return new PoinzFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
              return mContext.getString(R.string.poinz);
            case 1:
                return mContext.getString(R.string.places);
            default:
                return null;
        }
    }
}
