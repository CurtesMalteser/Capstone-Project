package com.curtesmalteser.pingpoinz.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.curtesmalteser.pingpoinz.BuildConfig;
import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.api.Event;
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

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;

    @BindView(R.id.tvDescription)
    AppCompatTextView tvDescription;

    @BindView(R.id.tvVenueName)
    AppCompatTextView tvVenueName;

    @BindView(R.id.tvCity)
    AppCompatTextView tvCity;

    @BindView(R.id.tvStart)
    AppCompatTextView tvStart;

    @BindView(R.id.tvEnd)
    AppCompatTextView tvEnd;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poinz_details);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra))) {
            event = getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
            makePicturesQuery(event.venueName());
            tvTitle.setText(event.title());
            String description = event.description() != null ? event.description() : "N/A";
            tvDescription.setText(Html.fromHtml(description));
            tvVenueName.setText(event.venueName());
            tvCity.setText(event.cityName());

            tvStart.setText(event.startTime());

            String stopTime = event.stopTime() != null ? event.stopTime() : "N/A";
            tvEnd.setText(stopTime);
        }
    }

    private void makePicturesQuery(String category) {
        UnsplashInterface apiInterface = UnsplashClient.getClient().create(UnsplashInterface.class);
        Call<SearchPhotoResult> call;

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

                if (response.body() != null && response.body().results().size() > 0) {
                    PicassoHelper.getPhoto(poinzPoster, response.body().results().get(i).urls().regular());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchPhotoResult> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

