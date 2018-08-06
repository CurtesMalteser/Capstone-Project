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

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
public class PoinzAdapter extends RecyclerView.Adapter<PoinzAdapter.PoinzPlacesViewHolder> {

    // TODO: 22/07/2018 Is Context needed?
    private Context mContext;
    private ArrayList<Event> mEvents;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Event event);
    }

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
                .inflate(R.layout.poinz_card, viewGroup, false));
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


        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvCategory)
        TextView tvCategory;

        @BindView(R.id.tvStart)
        TextView tvStart;

        @BindView(R.id.tvEnd)
        TextView tvEnd;

        public PoinzPlacesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            final Event event = mEvents.get(listIndex);

            tvTitle.setText(event.title());
            tvCategory.setText(event.cityName());
            tvStart.setText(event.startTime());
            tvEnd.setText(event.startTime());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Event eventModelList = mEvents.get(clickedPosition);
            mOnClickListener.onListItemClick(eventModelList);
        }
    }
}