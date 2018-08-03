package com.curtesmalteser.pingpoinz.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.activity.adapter.MapsFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // todo -> make the only portrait
    // TODO: 02/08/2018 -> make get the position from the activity and pass it into the fragments 

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.btnOpenMapsActivity)
    FloatingActionButton btnOpenMapsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppViewModel model = ViewModelProviders.of(this).get(AppViewModel.class);

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
