package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.api.Event;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.curtesmalteser.pingpoinz.data.utils.PicassoHelper.getPhoto;

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
public class PoinzAdapter extends RecyclerView.Adapter<PoinzAdapter.PoinzPlacesViewHolder> {

    // TODO: 22/07/2018 Is Context needed?
    private Context mContext;
    private ArrayList<Event> mEvents;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        //  void onListItemClick(ComposedPlacesModel moviesModel);
        void onListItemClick(Event event);
    }

    //public PoinzPlacesAdapter(Context context, ArrayList<ComposedPlacesModel> moviesModelArrayList,
    public PoinzAdapter(Context context, ArrayList<Event> events,
                        ListItemClickListener listener) {
        this.mContext = context;
        this.mEvents = events;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public PoinzAdapter.PoinzPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        return new PoinzAdapter.PoinzPlacesViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.poinz_places_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PoinzAdapter.PoinzPlacesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class PoinzPlacesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        @BindView(R.id.ivCardPlacePhoto)
        ImageView ivCardPlacePhoto;

        @BindView(R.id.tvPointName)
        TextView tvPointName;

        @BindView(R.id.tvPointAddress)
        TextView tvPointAddress;

        @BindView(R.id.tvAttributions)
        TextView tvAttributions;

        public PoinzPlacesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            final Event event = mEvents.get(listIndex);

            if (event.image() != null) {
                Picasso.get()
                        .load(event.image().medium().url())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(ivCardPlacePhoto, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get()
                                        .load(event.image().medium().url())
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .error(R.drawable.ic_launcher_background)
                                        .into(ivCardPlacePhoto);
                            }
                        });
            } else {
                Picasso.get().cancelRequest(ivCardPlacePhoto);
                ivCardPlacePhoto.setBackgroundResource(R.drawable.ic_launcher_background);
            }

            tvPointName.setText(event.title());
            tvPointAddress.setText(event.venueName());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            // ComposedPlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            Event moviesModelList = mEvents.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }
}