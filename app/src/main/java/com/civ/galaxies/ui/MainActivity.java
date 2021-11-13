package com.civ.galaxies.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.civ.galaxies.R;
import com.civ.galaxies.databinding.ActivityMainBinding;
import com.civ.galaxies.model.Planet;
import com.civ.galaxies.ui.adapter.PlanetBasicAdapter;
import com.civ.galaxies.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private final List<Planet> planetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        init();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init(){

        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading){
                showLoader("Please wait...");
            } else {
                hideLoader();
            }
        });
        viewModel.onError().observe(this, this::showLongToastMessage);
        viewModel.fetchPlanetData();

        PlanetBasicAdapter planetBasicAdapter = new PlanetBasicAdapter(planetList);

        viewModel.getPlanets().observe(this, planets -> {
            Log.e("init: ", "Got Planets : " + planets.size() );
            planetList.clear();
            planetList.addAll(planets);
            planetBasicAdapter.notifyDataSetChanged();
        });

        LinearLayoutManager mainLayoutManager = new LinearLayoutManager(this);
        binding.rvMain.setLayoutManager(mainLayoutManager);
        binding.rvMain.setAdapter(planetBasicAdapter);
    }
}