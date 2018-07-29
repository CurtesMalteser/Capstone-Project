package com.curtesmalteser.pingpoinz.data.utils;

import android.widget.ImageView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.api.Event;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
public class PicassoHelper {

    public static void getPhoto(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(url)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(imageView);
                    }
                });

    }
}
