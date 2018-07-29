package com.curtesmalteser.pingpoinz.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtesmalteser.pingpoinz.R;
import com.curtesmalteser.pingpoinz.data.maps.PlacesModel;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;

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

    private GeoDataClient mGeoDataClient;

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
        this.mGeoDataClient = Places.getGeoDataClient(mContext);
    }

    @NonNull
    @Override
    public PoinzPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        return new PoinzPlacesViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate( R.layout.poinz_places_card, viewGroup, false));
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

            // TODO: 28/07/2018 manage photos on a single model???
            //tvAttributions.setText(Html.fromHtml(model.placePhotoAttributions()));
            //ivCardPlacePhoto.setImageBitmap(model.placePhoto());


            // Request photos and metadata for the specified place.
            getPhotos(model.placeId(), ivCardPlacePhoto, tvAttributions);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
           // ComposedPlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            PlacesModel moviesModelList = mPlacesList.get(clickedPosition);
            mOnClickListener.onListItemClick(moviesModelList);
        }
    }

    // Request photos and metadata for the specified place.
    private void getPhotos(String placeId, ImageView imgView, TextView textView) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(task -> {
            // Get the list of photos.
            PlacePhotoMetadataResponse photos = task.getResult();
            // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0) {

                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();

                textView.setText(Html.fromHtml(attribution.toString()));

                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(task1 -> {
                    PlacePhotoResponse photo = task1.getResult();
                    Bitmap bitmap = photo.getBitmap();
                    imgView.setImageBitmap(bitmap);
                });
                photoMetadataBuffer.release();
            } else {
                imgView.setBackground(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
            }
        });
    }
}
