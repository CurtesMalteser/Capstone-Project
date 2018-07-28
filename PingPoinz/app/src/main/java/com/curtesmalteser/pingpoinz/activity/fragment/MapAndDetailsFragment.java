package com.curtesmalteser.pingpoinz.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtesmalteser.pingpoinz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapAndDetailsFragment extends Fragment {

    public MapAndDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_and_details, container, false);
    }

}
