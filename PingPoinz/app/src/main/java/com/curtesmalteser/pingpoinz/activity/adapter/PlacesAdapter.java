package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class PlacesAdapter
        extends RecyclerView.Adapter<PlacesAdapter.PoinzPlacesViewHolder> {

    // TODO: 22/07/2018 Is Context needed?
    private Context mContext;
    //private ArrayList<ComposedPlacesModel> mPlacesList;
    private ArrayList<PlacesModel> mPlacesList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(PlacesModel placesModel);
    }

    public PlacesAdapter(Context context, ArrayList<PlacesModel> placesModelArrayList,
                         ListItemClickListener listener) {
        this.mContext = context;
        this.mPlacesList = placesModelArrayList;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public PoinzPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        return new PoinzPlacesViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.places_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PoinzPlacesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }

    public class PoinzPlacesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;

        @BindView(R.id.tvRating)
        AppCompatTextView tvRating;

        @BindView(R.id.tvPriceLevel)
        AppCompatTextView tvPriceLevel;

        @BindView(R.id.tvAddress)
        AppCompatTextView tvAddress;

        PoinzPlacesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            final PlacesModel model = mPlacesList.get(listIndex);
            tvTitle.setText(model.placeName());
            String rating = model.placeRating() != -1.0 ? String.valueOf(model.placeRating()) : "N/A";
            tvRating.setText(rating);
            tvPriceLevel.setText(model.placePriceLevel());
            tvAddress.setText(model.placeAddress());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            PlacesModel placesModelList = mPlacesList.get(clickedPosition);
            mOnClickListener.onListItemClick(placesModelList);
        }
    }
}
