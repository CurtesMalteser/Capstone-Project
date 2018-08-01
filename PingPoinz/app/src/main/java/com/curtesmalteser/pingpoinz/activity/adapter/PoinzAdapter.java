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
import com.curtesmalteser.pingpoinz.data.api.Result;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by António "Curtes Malteser" Bastião on 29/07/2018.
 */
public class PoinzAdapter extends RecyclerView.Adapter<PoinzAdapter.PoinzPlacesViewHolder> {

    // TODO: 22/07/2018 Is Context needed?
    private Context mContext;
    private ArrayList<Result> mResults;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        //  void onListItemClick(ComposedPlacesModel moviesModel);
        void onListItemClick(Result event);
    }

    //public PoinzPlacesAdapter(Context context, ArrayList<ComposedPlacesModel> moviesModelArrayList,
    public PoinzAdapter(Context context, ArrayList<Result> events,
                        ListItemClickListener listener) {
        this.mContext = context;
        this.mResults = events;
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
        return mResults.size();
    }

    public class PoinzPlacesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tvPointName)
        TextView tvPointName;

        @BindView(R.id.tvPointAddress)
        TextView tvPointAddress;

        public PoinzPlacesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            final Result event = mResults.get(listIndex);


            tvPointName.setText(event.title());
            tvPointAddress.setText(event.description());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            // ComposedPlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            Result moviesModelList = mResults.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }
}