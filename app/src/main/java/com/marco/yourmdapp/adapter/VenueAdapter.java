package com.marco.yourmdapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marco.yourmdapp.R;
import com.marco.yourmdapp.model.primitives.Venue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueHolder> {

    private List<Venue> data = new ArrayList<>();

    public void setData(List<Venue> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public VenueHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new VenueHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VenueHolder venueHolder, int i) {
        Venue item = data.get(i);
        venueHolder.setDescription(item.getLocation().getFullAddress());
        venueHolder.setName(item.getName());
        venueHolder.setImage(R.drawable.location_icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class VenueHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.venue_description)
        TextView venue_desc;
        @BindView(R.id.venue_name)
        TextView venue_name;
        @BindView(R.id.venue_image)
        ImageView venue_image;

        VenueHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setName(String name) {
            venue_name.setText(name);
        }


        public void setDescription(String description) {
            venue_desc.setText(description);
        }


        public void setImage(int uri) {
            Glide.with(itemView)
                    .load(uri)
                    .into(venue_image);

        }
    }
}
