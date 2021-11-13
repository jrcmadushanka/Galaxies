package com.civ.galaxies.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.civ.galaxies.R;
import com.civ.galaxies.databinding.ActivityMainBinding;
import com.civ.galaxies.model.Planet;
import com.civ.galaxies.ui.adapter.PlanetBasicAdapter;
import com.civ.galaxies.utils.UiUtils;
import com.civ.galaxies.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final List<Planet> planetList = new ArrayList<>();
    private ActivityMainBinding binding;
    private HomeViewModel viewModel;
    private PlanetBasicAdapter planetBasicAdapter;
    private boolean hasMoreData = true;
    private boolean isAnimating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.setIsLoadingMore(true);
            } else {
                binding.setIsLoadingMore(false);
            }
        });
        viewModel.onError().observe(this, this::showLongToastMessage);
        viewModel.hasMoreData().observe(this, hasMoreData -> {
            Log.e("init: ", String.valueOf(hasMoreData));
            this.hasMoreData = hasMoreData;
        });

        viewModel.getPlanets().observe(this, planetResponse -> {
            int currentCount = planetList.size();
            planetList.addAll(planetResponse.getResults());
            planetBasicAdapter.notifyItemInserted(currentCount);
        });

        loadPlanets();

        planetBasicAdapter = new PlanetBasicAdapter(planetList, planet -> {
            UiUtils.showPlanetDetailsDialog(MainActivity.this, planet);
        });

        LinearLayoutManager mainLayoutManager = new LinearLayoutManager(this);
        binding.rvMain.setLayoutManager(mainLayoutManager);
        binding.rvMain.setAdapter(planetBasicAdapter);
        binding.rvMain.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            if (!binding.rvMain.canScrollVertically(1)) {
                loadPlanets();
            }

            if (mainLayoutManager.findFirstVisibleItemPosition() == 0 && binding.tvHeader.getAlpha() < 1f && !isAnimating) {
                isAnimating = true;
                binding.tvHeader.animate().setDuration(500).alpha(1f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isAnimating = false;
                    }
                }).start();
            } else if (mainLayoutManager.findFirstVisibleItemPosition() > 0 && binding.tvHeader.getAlpha() > 0f && !isAnimating) {
                binding.tvHeader.animate().setDuration(500).alpha(0f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isAnimating = false;
                    }
                }).start();
            }
        });
    }

    void loadPlanets() {
        if (hasMoreData) {
            Log.e("init: ", "Has data");
            viewModel.fetchPlanetData();
        }
    }
}