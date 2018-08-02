package com.curtesmalteser.pingpoinz.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.curtesmalteser.pingpoinz.BuildConfig;
import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.api.Result;
import com.curtesmalteser.pingpoinz.data.api.SearchPhotoResult;
import com.curtesmalteser.pingpoinz.data.api.UnsplashClient;
import com.curtesmalteser.pingpoinz.data.api.UnsplashInterface;
import com.curtesmalteser.pingpoinz.data.utils.PicassoHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PoinzDetailsActivity extends AppCompatActivity {

    @BindView(R.id.poinzPoster)
    AppCompatImageView poinzPoster;

    Result event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poinz_details);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra))) {
            event = getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
            makePicturesQuery(event.category());
        }
    }


    private void makePicturesQuery(String category) {
        UnsplashInterface apiInterface = UnsplashClient.getClient().create(UnsplashInterface.class);
        Call<SearchPhotoResult> call;

       /* if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {*/

        Random r = new Random();
        int i = r.nextInt(9);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("orientation", "landscape");
        queryParams.put("query", category);
        queryParams.put("client_id", BuildConfig.UNSPLASH_ACCESS_KEY);

        call = apiInterface.getEventfulEvents(queryParams);
        call.enqueue(new Callback<SearchPhotoResult>() {
            @Override
            public void onResponse(@NonNull Call<SearchPhotoResult> call, @NonNull Response<SearchPhotoResult> response) {

                PicassoHelper.getPhoto(poinzPoster, response.body().results().get(i).urls().regular());

            }

            @Override
            public void onFailure(@NonNull Call<SearchPhotoResult> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
       /* } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();*/
    }

}
