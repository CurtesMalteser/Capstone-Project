package com.curtesmalteser.pingpoinz.data.db;

import android.content.Context;
import android.os.AsyncTask;

import com.curtesmalteser.pingpoinz.data.api.Event;
import com.curtesmalteser.pingpoinz.data.api.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 04/08/2018.
 */
public class StoreEventsAsync extends AsyncTask<Object, Void, Void> {

    private PoinzDatabase mDb;
    private PoinzDao mPoinzDao;
    private Context context;

    @Override
    protected Void doInBackground(Object... params) {
        context = (Context) params[0];
        // TODO: 04/08/2018 check if LiveData works with observing the class Events that has a List
        // and check if makes sense keep autovalue for these classes
        List<Event> events = (List<Event>) params[1];
        ArrayList<EventDbModel> mEventDbModel = new ArrayList<>();
        mDb = PoinzDatabase.getDatabase(context);
        mPoinzDao = mDb.poinzDao();

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

        mPoinzDao.addCurrencies(mEventDbModel);

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
