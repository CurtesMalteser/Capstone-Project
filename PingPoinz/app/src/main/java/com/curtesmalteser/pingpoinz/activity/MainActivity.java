package com.curtesmalteser.pingpoinz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.adapter.MapsFragmentPagerAdapter;
import com.curtesmalteser.pingpoinz.data.api.Collections;
import com.curtesmalteser.pingpoinz.data.api.PredictHqClient;
import com.curtesmalteser.pingpoinz.data.api.PredictHqInterface;
import com.curtesmalteser.pingpoinz.data.api.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.btnOpenMapsActivity)
    FloatingActionButton btnOpenMapsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnOpenMapsActivity.bringToFront();
        btnOpenMapsActivity.setOnClickListener(l -> {
            Intent i = new Intent(this, MapActivity.class);
            startActivity(i);
        });


        MapsFragmentPagerAdapter adapter =
                new MapsFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
