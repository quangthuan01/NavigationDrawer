package com.example.navigationdrawer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.GioiThieu;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GioiThieuAdapter extends RecyclerView.Adapter<GioiThieuAdapter.TravelLocationViewHolder> {
    private List<GioiThieu> gioiThieus;

    public GioiThieuAdapter(List<GioiThieu> gioiThieus) {
        this.gioiThieus = gioiThieus;
    }

    @NonNull
    @Override
    public TravelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelLocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_location, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull GioiThieuAdapter.TravelLocationViewHolder holder, int position) {
        holder.setLocationData(gioiThieus.get(position));
    }

    @Override
    public int getItemCount() {
        return gioiThieus.size();
    }

    static class TravelLocationViewHolder extends RecyclerView.ViewHolder {
        private KenBurnsView kbvLocation;
        private TextView textTitle, textLocation, textStarRating;

        public TravelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvLocation);
            textTitle = itemView.findViewById(R.id.textTitleLocation);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
        }

        void setLocationData(GioiThieu gioiThieu) {
            Picasso.get().load(gioiThieu.imageUrl).into(kbvLocation);
            textTitle.setText(gioiThieu.title);
            textLocation.setText(gioiThieu.location);
            textStarRating.setText(String.valueOf(gioiThieu.starRating));

        }
    }
}
