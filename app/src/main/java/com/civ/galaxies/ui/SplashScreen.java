package com.civ.galaxies.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.civ.galaxies.R;
import com.civ.galaxies.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        binding.ivGalaxyText.animate().setDuration(2000).setStartDelay(1000).alpha(1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }).start();
        binding.ivPlanet.animate().setDuration(1500).setStartDelay(500).alpha(1f).start();
    }
}