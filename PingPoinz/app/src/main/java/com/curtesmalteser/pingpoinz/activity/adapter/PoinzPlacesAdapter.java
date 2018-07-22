package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.PlacesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by António "Curtes Malteser" Bastião on 22/07/2018.
 */
public class PoinzPlacesAdapter extends RecyclerView.Adapter<PoinzPlacesAdapter.PoinzPlacesViewHolder> {

    // TODO: 22/07/2018 Is Context needed?
    private Context mContext;
    private ArrayList<PlacesModel> mPlacesList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(PlacesModel moviesModel);
    }

    public PoinzPlacesAdapter(Context context, ArrayList<PlacesModel> moviesModelArrayList,
                              ListItemClickListener listener) {
        this.mContext = context;
        this.mPlacesList = moviesModelArrayList;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public PoinzPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.poinz_places_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new PoinzPlacesViewHolder(view);
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

        // ImageView poster;

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
            final PlacesModel model = mPlacesList.get(listIndex);
            tvPointName.setText(model.placeName());
            tvPointAddress.setText(model.placeAddress());


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            PlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }
}
