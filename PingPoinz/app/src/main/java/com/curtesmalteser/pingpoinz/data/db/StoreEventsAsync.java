package com.curtesmalteser.pingpoinz.data.db;

import android.content.Context;
import android.os.AsyncTask;

import com.curtesmalteser.pingpoinz.data.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class StoreEventsAsync extends AsyncTask<Object, Void, Void> {

    @Override
    protected Void doInBackground(Object... params) {
        Context context = (Context) params[0];
        @SuppressWarnings("unchecked")
        List<Event> events = (List<Event>) params[1];
        ArrayList<EventDbModel> mEventDbModel = new ArrayList<>();
        PoinzDatabase mDb = PoinzDatabase.getDatabase(context);
        PoinzDao mPoinzDao = mDb.poinzDao();

        mPoinzDao.deleteEventsTable();

        for (Event event : events) {
            mEventDbModel.add(new EventDbModel(
                    event.regionAbbr(),
                    event.postalCode(),
                    event.latitude(),
                    event.longitude(),
                    event.id(),
                    event.cityName(),
                    event.countryName(),
                    event.countryAbbr(),
                    event.regionName(),
                    event.startTime(),
                    event.venueDisplay(),
                    event.title(),
                    event.stopTime(),
                    event.venueName()
            ));
        }

        mPoinzDao.addEvents(mEventDbModel);

        return null;
    }


       /* @Override
        protected void onPostExecute(String result) {
            Log.d("lol", result);

            Intent intent = new Intent(context.getApplicationContext(), JokesActivity.class);
            intent.putExtra(context.getResources().getString(R.string.string_intent_joke), result);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }*/
    //}
}
