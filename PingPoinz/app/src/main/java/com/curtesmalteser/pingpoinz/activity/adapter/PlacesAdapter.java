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
        //  void onListItemClick(ComposedPlacesModel moviesModel);
        void onListItemClick(PlacesModel moviesModel);
    }

    //public PoinzPlacesAdapter(Context context, ArrayList<ComposedPlacesModel> moviesModelArrayList,
    public PlacesAdapter(Context context, ArrayList<PlacesModel> moviesModelArrayList,
                         ListItemClickListener listener) {
        this.mContext = context;
        this.mPlacesList = moviesModelArrayList;
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
            // final ComposedPlacesModel model = mPlacesList.get(listIndex);
            final PlacesModel model = mPlacesList.get(listIndex);
            tvPointName.setText(model.placeName());
            tvPointAddress.setText(model.placeAddress());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            // ComposedPlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            PlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }
}
