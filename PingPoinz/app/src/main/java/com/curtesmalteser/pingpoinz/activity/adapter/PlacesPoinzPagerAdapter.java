package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by António "Curtes Malteser" Bastião on 28/07/2018.
 */
public class PlacesPoinzPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PlacesPoinzPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
