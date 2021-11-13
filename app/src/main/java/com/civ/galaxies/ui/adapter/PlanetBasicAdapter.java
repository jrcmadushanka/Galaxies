package com.civ.galaxies.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civ.galaxies.R;
import com.civ.galaxies.databinding.CardPlanetBinding;
import com.civ.galaxies.interfaces.OnPlanetSelectListener;
import com.civ.galaxies.model.Planet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class PlanetBasicAdapter extends RecyclerView.Adapter<PlanetBasicAdapter.ViewHolder> {
    List<Planet> planetList;
    OnPlanetSelectListener onPlanetSelectListener;

    public PlanetBasicAdapter(List<Planet> planetList, OnPlanetSelectListener onPlanetSelectListener) {
        this.planetList = planetList;
        this.onPlanetSelectListener = onPlanetSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardPlanetBinding binding = CardPlanetBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.bind(planetList.get(position));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardPlanetBinding binding;

        public ViewHolder(@NonNull CardPlanetBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(Planet planet) {
            binding.setPlanetViewModel(planet);
            planet.setImageId(new Random().nextInt(800));
            binding.getRoot().setOnClickListener(view -> {
                onPlanetSelectListener.onPlanetClick(planet);
            });
            Picasso.get().load("https://picsum.photos/id/" + planet.getImageId() + "/400/200").fit().placeholder(R.drawable.galaxy_logo).into(binding.ivMainImage);
        }
    }
}
